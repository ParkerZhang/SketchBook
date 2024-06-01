<template>
  <img alt="Vue logo" src="./assets/logo.png"/>
  <li>list indecies</li>
  <SearchAutocomplete
      :items="index"
      :isLoading="loading"
      :is-async="false"
      @selected="searchIndex = $event"
  >
  </SearchAutocomplete>

  <li>Search in all indices</li>
  <SearchAutocomplete
      :items="allIndex"
      :isLoading="false"
      :is-async="true"
      @suggest="searchAll"
  >
  </SearchAutocomplete>

  <ul>
    {{
      info
    }}
  </ul>
</template>

<script>
import SearchAutocomplete from './components/SearchAutocomplete.vue'
import axios from "axios"


import {SearchDriver} from "@elastic/search-ui";
import config from "../searchConfig";

export default {
  name: 'App',
  components: {
    SearchAutocomplete
  },
  data() {
    return {
      info: null,
      loading: false,
      index: [],
      suggestion: [],
      allIndex: []
    }
  },
  mounted() {

    const auth = 'ApiKey ' + import.meta.env.VITE_APP_ES_APIKEY;
    axios.get('http://localhost:3000/_cat/indices?h=index', {headers: {Authorization: 'ApiKey ' + import.meta.env.VITE_APP_ES_APIKEY}})
        .then(response => {
          console.log(response);
          var x = response.data.split('\n');

          var short =[]
          x.forEach(s => {
            if (! s.startsWith('.') && s != 'metrics-endpoint.metadata_current_default')

              short.push(s);
          })
          this.index = short
          this.loading = false
        });

  },
  methods: {


    searchAll_local(hint) {
      axios.get("./data/result.json")
          .then(response => {
                console.log("search.local.then")
                var x = response.data;
                var short = x.map(j => {
                  var s = JSON.stringify(j)
                  var i = s.toUpperCase().indexOf(hint.toUpperCase())
                  return j._index.toString() + ": ..." + s.substring(i - 10, i + 100) + "...  _id:" + j._id
                })
                this.allIndex = short.slice(0,20)
              }
          );
    },

    searchAll(hint) {
      if (this.loading === true)
        return;
      this.loading = true
      const size = 50
      axios.post(
          "http://localhost:3000/_search",
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
              Authorization: 'ApiKey ' + import.meta.env.VITE_APP_ES_APIKEY
            },
          }
      )
          .then(response => {
                console.log("search.local.then")
                var x = response.data.hits.hits;
                // console.log(x);
                var short = x.map(j => {
                  var s = JSON.stringify(j)
                  var i = s.toUpperCase().indexOf(hint.toUpperCase())
                  return j._index.toString() + ": ..." + s.substring(i - 10, i + 100) + "...  _id:" + j._id
                })
                console.log(short[0])
                this.allIndex = short.slice(0, 20)
                this.loading = false
              }
          );
    }
  }
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
