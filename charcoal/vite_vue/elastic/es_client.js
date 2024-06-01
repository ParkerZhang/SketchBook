// import { Client } from '@elastic/elasticsearch'
// import fs from "fs"
import axios from "axios"
//
// const client = new Client({
//   // node: 'https://localhost:9200', // Elasticsearch endpoint
//     node: 'http://localhost:3000',
//   auth: {
//     apiKey:  process.env.VITE_APP_ES_APIKEY,
//     }
//     ,
//     tls: {
//         ca: fs.readFileSync('./http_ca.crt'),
//         rejectUnauthorized: false
//       }
// })
// process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = 0;
const hint = "london"
const size = 100
const hintUpper = hint.toUpperCase()

axios.post(
    'http://localhost:3000/user/_search',
    {
        "query": {
            "simple_query_string": {
                "query": hint + "*"
            }
        },
        "size": size,
        "sort": [{"_score": "desc"}]
    }
    ,
    {
        headers: {
            Authorization: "APIKEY ****"
        },
    }
)
    .then(response => {
            console.log("search.local.then")
            var x = response.data.hits.hits;
            console.log(x);
        });


// var result= await client.search({
//     "query": {
//         "simple_query_string": {
//           "query": hintUpper
//         }
//       },
//       "size": size,
//       "sort": [ {"_score" : "desc"}]
//   })

//
// fs.writeFileSync('./data/result.json',JSON.stringify(result.hits.hits))
// var f=fs.readFileSync('./data/result.json')
// var x = JSON.parse(f)
// var indices = [ ... new Set(x.map( a => {  return a._index.toString();}))]
// const y= x.map(JSON.stringify)
// const short = y.map( s => { var i = s.toUpperCase().indexOf(hintUpper); console.log(i);
//         if ( i<0 )
//             return s.substring(0,40)
//         else
//             return s.substring(i-10, i+30)
//     })
// console.log(y[1])
// console.log(short)
//

