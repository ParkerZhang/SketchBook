<template>
  <div class="vpo-object">
    <RenderKeyValue v-for="key in Object.keys(printableObject)"
                      :printable-key="key"
                      :printable-value="printableObject[key]"
                      :is-array="isArray"
                      :is-last-element="Object.keys(printableObject).pop() === key"
                      :initial-collapsed="initialCollapsed"></RenderKeyValue>
  </div>
</template>

<script >
import {h} from "vue"
// import RenderKeyValue from "./RenderKeyValue.js"
export default {
  props: {
    printableObject: {
      default: {},
      type: [Object, Array]
    },
    isArray : {
      default: false,
      type: [Boolean, Array]
    },
    initialCollapsed: {
      default: false,
      type: [Boolean, Array]
    }
  },
  // setup : () => {
  //   console.log("RenderObject.setup")
  //   console.log(this.printableObject)
  //   return h(RenderKeyValue, {printableValue : this.printableObject[0], printableKey : 0 , isArray:false, isLastElement: true,initialCollapsed:false})
  // },
  components: {

    RenderKeyValue:()=> import("./RenderKeyValue.js")
  }
}
</script>