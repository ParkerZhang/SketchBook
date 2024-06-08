<template>

    <div class="col">
      <h2>Uncollapsed</h2>
<!--<PrintObject :printable-object="myObject" ></PrintObject>-->
<tree :my-key = "'tree'" :value="comment"> </tree>
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
import PrintObject from "./components/PrintObject.vue"
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
  computed: {
    comment() {
      return {
        a: 1,
        b: "hello",
        c: {
          d: [3, 4],
          e: {
            f: "test"
          }
        },
        g: false
      }
    }
  },

  components: {

    PrintObject,
    tree
  }
  ,
  setup() {
    const hasChildren = "false"
    return {
      hasChildren
    }
  }
}
</script>