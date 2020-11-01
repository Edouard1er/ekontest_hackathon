if (process.env.FUNCTIONS_EMULATOR) {
    process.env.GOOGLE_APPLICATION_CREDENTIAL = "./ekontesthackathon-firebase-adminsdk-zuclg-6e14b73692.json"
  }
  
  const functions = require('firebase-functions');
  var moncash = require('nodejs-moncash-sdk');

  moncash.configure({
    'mode': 'live', //sandbox or live
    'client_id': 'f472319a575420082d2aee7a226c2865',
    'client_secret': '_WDlVgFcbMw19kMEGLjhvev36WQBWVmcRDwgZ3Mlk8dBM8Hn-TpeRjuj-RtqJwgD'
    });
  
  // The Firebase Admin SDK to access Cloud Firestore.
  const admin = require('firebase-admin');
  const axios = require('axios').default;
  
  var serviceAccount = require("./ekontesthackathon-firebase-adminsdk-zuclg-6e14b73692.json");
  
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://ekontesthackathon.firebaseio.com/"
  });

  
  const runtimeOpts = {
    timeoutSeconds: 300,
    memory: '1GB'
  }

  //here is where we get MonCash Transaction Detail(mcdt, moncash transaction detail)
  exports.getmctd = functions.https.onRequest(async (req, res) => {
      console.log("Execution du fonction")
      console.log("Saving transaction detail...")
      console.log("Request type: " + req.method)
      console.log("Requ params", req.query)
      var trId = req.query.transactionId
      

      // Get payment by Transaction_id
    moncash.capture.getByTransactionId(trId.toString(), function (error, capture) {
      if (error) {
        console.error(error);
      } else {
        console.log("le type de capture est: " + typeof capture);
        admin.database().ref("/Temp").push(capture)

        var reference = capture.payment.reference
        var message = capture.payment.message
        var status = capture.status

        console.log("Message: " + message)
        console.log("Status: " + status)
        console.log("reference: " + reference)

        admin.database().ref("/TransactionModel").orderByChild("orderId").equalTo(reference).on("child_added", async (snapshot) => {
          var transactionId = snapshot.val().idTransaction
          var transactionId2 = snapshot.val()[1]
          // admin.database().ref("/Temp").push(snapshot.val())

          // console.log("type de snapshot: " + typeof snapshot)
          // console.log("Transaction key 1: " + transactionId1)
          // console.log("Transaction key 2: " + transactionId2)

          admin.database().ref("/TransactionModel/" + transactionId + "/transactionKey").set(trId.toString())
          // admin.database().ref("/TransactionModel/" + transactionId + "/data").set(capture)

        })
      }
    });
      res.send("ok")
  })

  exports.submitScan = functions.database.ref("/Documents/PaidDoc/{documentID}").onCreate((snapshot, context) => {
    console.log("[submitScan] \n" + snapshot._data);
    //let's create the api call to get api key
    var email = "sergioosson@gmail.com";
    var key = "b0e5f846-18c0-4e01-875f-d8208cf74a01";

    axios(prepareToken(email, key))
        .then((response) => {
          console.log("[submitScan] Token reveived successfully");
          // console.log(response.data.access_token);
          //let's prepare now to send the scan file to copyleaks
          axios(prepareDocument(response.data.access_token, snapshot._data.url, snapshot._data.scanId))
            .then((response) => {
              console.log("[submitScan] text submitted successfully");
              console.log(response.status)
              return
            })
            .catch(function(error) {
              console.log("[submitScan] showing error 2");
              if (error.response) {
                // The request was made and the server responded with a status code
                // that falls out of the range of 2xx
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
              } else if (error.request) {
                // The request was made but no response was received
                // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                // http.ClientRequest in node.js
                console.log(error.request);
              } else {
                // Something happened in setting up the request that triggered an Error
                console.log('[submitScan] Error:', error);
              }
            })
            return
        })
        .catch( (error) => {
          console.log("[submitScan] showing error 1");
          if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log(error.request);
          } else {
            // Something happened in setting up the request that triggered an Error
            console.log('[submitScan] Error:', error);
          }
        })
    return;  
})

  exports.getScanCompletion = functions.https.onRequest(async (req, res) => {
  console.log("[getScanCompletion] \n" + req.body);
  //generate sharing code
  const write = await admin.database().ref("/ScanResults").push(req.body);
  
  // console.log(ref);
  admin.database().ref("/Documents/PaidDoc").orderByChild("scanId").equalTo(req.body.scannedDocument.scanId).on("child_added", async (snapshot) => {
    const paidDocKey = snapshot.key;
    console.log("[getScanCompletion] \n Id du document: \n" + paidDocKey);
    admin.database().ref("/Documents/PaidDoc/" + snapshot.key + "/scanResultId").set(write.key);
    var score = req.body.results.score.aggregatedScore;

    //   =========>>>>> generate code
    let generatedKey = ""
    do {
      console.log("Inside of while")
      //generate the key
      console.log("New key generated")
      generatedKey = generateSharingCode()
      console.log("Generated key: " + generatedKey)
      temoin = false
      //here we check if the generated code has already been generated
      Promise.all(admin.database().ref("/GeneratedCode").orderByChild("key").equalTo(generatedKey).once("value", (snapshot) => {
        console.log("Inside of admin\n")
        if(snapshot.exists()) {
          console.log(snapshot.val())
          temoin = true  
        }
      })).catch(reason => {
        console.log(reason)
      })
    } while (temoin);
    //================>>>>>*/
    
    console.log("Aggregated score: " + score);
    if(score <= 20) {
      //if the score from copyleaks is less or equal than 20% we accept the doc
      console.log("Score is less than 20");
      admin.database().ref("/Documents/PaidDoc/" + paidDocKey + "/status").set("Accepted");
      admin.database().ref("/Documents/PaidDoc/" + paidDocKey + "/sharingCode").set(generatedKey);
      //we add the generated code in the db
      admin.database().ref("/GeneratedCode").push({"key": generatedKey})

    } else if(score > 20 && score <= 50) {
      //if the score from copyleaks is between 20% and 50% we check deeply with AI
      console.log("Score is between 20 and 50");
      admin.database().ref("/Documents/PaidDoc/" + paidDocKey + "/status").set("In Progess");
      //AI check here
    } else {
      //if the score from copyleaks is greater than 50% we refuse the document
      console.log("Score is more than 50");
      admin.database().ref("/Documents/PaidDoc/" + paidDocKey + "/status").set("Refused");
    }
  });
  res.json("success");
})

  var prepareToken = (email, key) => {
        var headers = {
            'Content-type': 'application/json'
        };
        var dataString = '{\
          "email": "' + email + '",\
          "key": "' + key + '"\
        }';
        
        var options = {
            url: 'https://id.copyleaks.com/v3/account/login/api',
            method: 'POST',
            headers: headers,
            data: JSON.parse(dataString)
        };

        return options;
  }
 
  var prepareDocument = (token, url, scanId) => {
    //the first thing is to generate the scan id
    console.log("[prepareDocument] scanId: " + scanId);

    var headers = {
      'Authorization': 'Bearer ' + token,
      'Content-type': 'application/json'
    };

    var status = "https://us-central1-ekontesthackathon.cloudfunctions.net/getScanCompletion?status={STATUS}&scanID=" + scanId;

    var dataString = '{\
      "url": "' + url + '",\
      "properties": {\
        "sandbox": true,\
        "sensitivity": 5,\
        "pdf": {\
          "create": true\
        },\
        "webhooks": {\
          "status": "' + status + '"\
        }\
      }\
    }';

    var options = {
      url: 'https://api.copyleaks.com/v3/education/submit/url/' + scanId,
      method: 'PUT',
      headers: headers,
      data: JSON.parse(dataString)
    };

    return options;
  }

  const generateSharingCode = () => {
    const values = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", 
                    "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    var generatedKey = ""

    //we generate a 5 lenght key to be shared
    for(var i = 0; i <= 4; i++) {
      generatedKey += values[Math.floor((Math.random() * 35) + 0)]
    }

    return generatedKey
  }