<template>
  <div :class="[{'vpo-key-value__hover': this.keyHover}, 'vpo-key-value']" v-on:click.stop="collapse" >
    <span v-on:mouseenter.="mouseenter" v-on:mouseleave.self="mouseleave" > <RenderKey class="vpo-key__object" :printable-key="myKey"/> <RenderBracket :is-array="isArray" :is-opening-bracket=true /> </span>
    <div v-if="!objectCollapsed" class="vpo-object">
      <div class="vpo-key-value" id="eachElement" v-for="x in Object.keys(value)">
        <span id="arrayElement" class="vpo-object" v-if="typeof value ==='object'  && Array.isArray(value) "> <RenderValue
            :printable-value="value[x]"/></span>
        <span class="vpo-key__object" id="childOjbect" v-else-if="typeof value[x] === 'object'"  >
          <tree :myKey="x" :value="value[x]" :isArray=" Array.isArray(value[x])" v-on:mouseover="mouseleave"  />
        </span>
        <span v-else> <RenderKey :printable-key="x"/> <RenderValue :printable-value="value[x]"/> </span>
      </div>
    </div>
    <div v-else><span v-on:click.stop="collapse" >...</span></div>
    <span v-on:mouseenter.="mouseenter" v-on:mouseleave.self="mouseleave" > <RenderBracket :is-array="isArray" :is-opening-bracket=false :is-last-element=true /> </span>
  </div>

</template>

<script>
import RenderValue from "./PrintObject/RenderValue.vue";
import RenderBracket from "./PrintObject/RenderBracket.vue";
import RenderKey from "./PrintObject/RenderKey.vue";

export default {
  name: "tree",
  props: {
    myKey: {},
    value: {},
    isArray: {default: false},
    isCollapsed:{default: false}
  },
  data: () => {
    console.log("RenderKeyValue.data")
    return {
      objectCollapsed: false,
      keyHover: false
    }
  },

  components: {RenderKey, RenderValue, RenderBracket}
  ,
  methods: {
    collapse () {
      this.objectCollapsed = !this.objectCollapsed
      this.keyHover = false
      console.log(this.myKey + " clicked")
    },
    mouseenter () {
      this.keyHover = true
      console.log(this.myKey + " enter")
    },
    mouseleave () {
      this.keyHover = false
      console.log(this.myKey+ " leave")
    }
  },
  // computed:{
  //   isInitialCollapsed () {
  //     return this.isCollapsed;
  //   }
  // }
  mounted () {
    this.objectCollapsed = false
  }
}
</script>

<style>
.vpo-wrapper {
  display: inline-block;
  font-family: monospace;
}

.vpo-object {
  padding-left: 1em;
}

.vpo-key {
  margin-right: 4px;
}

.vpo-value--number {
  color: blue
}

.vpo-value--string {
  color: green
}

.vpo-value--boolean {
  color: red
}

.vpo-value--null {
  color: purple
}

.vpo-key-value {
  padding-left: 1px;
  border-left: 1px solid #ddd;
}

.vpo-key__object:hover {
  cursor: pointer;
}

.vpo-key-value__hover {
  background-color: lightgrey;
}
</style>
