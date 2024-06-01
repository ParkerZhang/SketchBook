import express from 'express';
import cors from 'cors';
const server = express();
import axios from "axios"
server.use(express.json());
server.use(cors( {
    original: "http://localhost:*"
}))
server.use(express.static("."));


server.post(/.*/, postElastic);
server.get(/.*/,getElastic);
function postElastic(req, res)  {
    // var hint = "london"
    // var size = 30

    process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = 0;
    var x= req.body
    var url = req.url
    var parameter = req.params
    var auth = req.headers["authorization"]

    axios.post(
        "https:/localhost:9200"+req.url,
        x
        ,
        {
            headers: {
                Authorization: auth
            },
        }
    ).then(response => {
        res.send( response.data);
    });
};

function getElastic(req, res)  {
    // var hint = "london"
    // var size = 30
    console.log("/_cat")
    process.env['NODE_TLS_REJECT_UNAUTHORIZED'] = 0;
    var x= req.body
    var url = req.url
    var parameter = req.params
    var auth = req.headers["authorization"]

    axios.get(
        "https:/localhost:9200"+req.url
        ,
        {
            headers: {
                Authorization: auth
            },
        }
    ).then(response => {
        res.send( response.data);
    });
};

server.listen(3000, () => {
    console.log('ready');
});