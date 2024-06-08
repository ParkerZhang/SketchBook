import RenderKey from './RenderKey.vue'
import RenderValue from './RenderValue.vue'
import RenderObject from './RenderObject.vue'
import RenderBracket from './RenderBracket.vue'
import {h} from 'vue'

console.log("RenderKeyValue.script")
export default {
  props: {
      printableKey : {} ,
      printableValue : {},
      isArray : {} ,
      isLastElement: {} ,
      initialCollapsed: {}
  }
,
  data: () => {
    console.log("RenderKeyValue.data")
    return {
      objectCollapsed: false,
      keyHover: false
    }

  },
  components: {
    RenderKey,
    RenderValue,
    RenderObject,
    RenderBracket
  },
  computed: {
    isInitialCollapsed () {
      console.log("computed")
      const type = typeof this.initialCollapsed
      if (type === 'boolean') {
        return !!this.initialCollapsed
      } else if ((type === 'object') && Array.isArray(this.initialCollapsed)) {
        return this.initialCollapsed.includes(this.printableKey)
      } else {
        console.warn('initialCollapsed needs to be either boolean or array, your are setting: ' + type)
      }
    }
  },
  methods: {
    collapse () {
      this.objectCollapsed = !this.objectCollapsed
      this.keyHover = false
    },
    mouseenter () {
      this.keyHover = true
    },
    mouseleave () {
      this.keyHover = false
    }
  },
  mounted () {
    this.objectCollapsed = this.isInitialCollapsed
  },
  render: function () {

      let valueIsObject = typeof this.printableValue === 'object' && this.printableValue !== null
      let children = []
      console.log([this.printableKey, this.printableValue, this.isArray, this.isLastElement, this.initialCollapsed  ])
      // print "normal" key
      if (this.printableKey && !this.isArray) {
        if (valueIsObject && Object.keys(this.printableValue).length > 0) {
          children.push(h(RenderKey,  
            // nativeOn: {click: this.collapse, mouseenter: this.mouseenter, mouseleave: this.mouseleave},
              {printableKey: this.printableKey},
            {class: 'vpo-key__object'}
          ))
        } else {
          children.push(h(RenderKey, {printableKey: this.printableKey}))
        }
      }
  
      if (valueIsObject) {
        const isArray = Array.isArray(this.printableValue)
  
        children.push(h(RenderBracket, {
          isArray: isArray, isOpeningBracket: true},
          // nativeOn: {
          //   click: this.collapse,
          //   mouseenter: this.mouseenter,
          //   mouseleave: this.mouseleave
          // }
        ))
        if (this.objectCollapsed) {
          children.push(h('span', {
            on: {
              click: this.collapse,
              mouseenter: this.mouseenter,
              mouseleave: this.mouseleave
            }
          }, '...'))
        } else {
          if (Object.keys(this.printableValue).length > 0)
          {
            children.push( h(RenderObject, {
              printableKey:this.printableKey,
              printableObject: this.printableValue,
              isArray: isArray,
              initialCollapsed: this.initialCollapsed

              }
            ));
            console.log(this.printableValue)
          }
        }
        children.push(h(RenderBracket,  
            {isArray: isArray, isLastElement: this.isLastElement},
          // nativeOn: {
          //   click: this.collapse,
          //   mouseenter: this.mouseenter,
          //   mouseleave: this.mouseleave
          // }
         ))
      } else {
        children.push(h(RenderValue, {

            printableValue: this.printableValue,
            isLastElement: this.isLastElement
          }
        ))
      }

      // var div = h(RenderValue,{printableValue:"abc"})
      var div= h('div', {class: [{'vpo-key-value__hover': this.keyHover}, 'vpo-key-value']},children)

      return div
  }
}
