<template>
  <div class="col">
    <div class="vpo-wrapper"><tree my-key=x :value="myObject" :is-collapsed="true"></tree></div>
  </div>
</template>

<style>
.col {
  display: inline-block;
  vertical-align: top;
  width: 25%;
}
</style>

<script>
import tree from "./components/tree.vue"

export default {
  data: () => {
    return {
      myObject: {
        a: 1,
        b: "hello",
        c: {
          d: [3, 4],
          e: {
            f: "test"
          }
        },
        g: false
      },
      simple: ["A"]
    }
  },

  components: {
    tree
  }

}
</script>