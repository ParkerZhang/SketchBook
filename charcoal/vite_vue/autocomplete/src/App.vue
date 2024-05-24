<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <SearchAutocomplete :items="['Apple',
    'Banana',
    'Orange',
    'Mango'
  ]" @selected="item = $event"
  :is-async=false>
  </SearchAutocomplete>
  <ui>{{ info }}</ui>
</template>

<script>
import SearchAutocomplete from './components/SearchAutocomplete.vue'
import axios from "axios"
export default {
  name: 'App',
  components: {
    SearchAutocomplete
  },
  data() {
    return {
      info :null
    }
  },
  mounted() {
    axios.get('https://api.coindesk.com/v1/bpi/currentprice.json')
      .then(response => (this.info = response))
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
