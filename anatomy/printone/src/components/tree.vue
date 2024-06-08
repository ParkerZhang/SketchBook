<template>
  <div>
    <RenderKey :printable-key="myKey"/> <RenderBracket :is-array="isArray" :is-opening-bracket=true />
  <div id="eachElement" v-for="x in Object.keys(value)">
    <div id = "arrayElement" v-if="typeof value ==='object'  && Array.isArray(value) " > <RenderValue :printable-value="value[x]" /></div >
    <div id = "childOjbect"  v-else-if="typeof value[x] === 'object'">
       <tree :myKey="x" :value="value[x]" :isArray=" Array.isArray(value[x])" />
    </div>
    <div id ="normalElement" v-else> <RenderKey :printable-key="x"/> <RenderValue :printable-value="value[x]"/>  </div>
  </div >
  <RenderBracket :is-array="isArray" :is-opening-bracket=false  :is-last-element=true  />
  </div>
</template>

<script>
import RenderValue  from "./PrintObject/RenderValue.vue";
import RenderBracket from "./PrintObject/RenderBracket.vue";
import RenderKey from "./PrintObject/RenderKey.vue";

export default {
  name: "tree",
  props: {
    myKey: {},
    value: {},
    isArray:{}
  },
  components : {RenderKey, RenderValue, RenderBracket}
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
