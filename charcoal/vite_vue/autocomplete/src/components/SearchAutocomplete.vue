<template>
    <div class="autocomplete">
        <input v-model="search" type ="text"
        @input="onChange" 
        @keydown.down="onArrowDown"
        @keydown.up="onArrowUp"
        @keydown.enter="onEnter"
        />
        <ul v-show="isOpen" class="autocomplete-results">
        <li
            v-if="isLoading"   
            class="loading"
        > Loading results...</li>
        <li v-else
        v-for="(result, i) in results"  
        :key="i" 
        @click="setResult(result)" 
        class="autocomplete-result"
        :class="{ 'is-active' : i=== arrowCounter }"
        > {{result}}</li></ul>
    </div>
</template>

<script>
export default {
    name: 'SearchAutocomplete',
    props: {
        items: {
            type: Array,
            required: false,
            default :() => [],
        },
        isAsync: {
            type:Boolean,
            required:false,
            default:false,
        },

    },
    data() {
        return {
            search: '',
            results: [],
            isOpen: false,
            arrowCounter: -1
        };
    },
    mounted(){
        document.addEventListener('click',this.handleClickOutside);
    },
    unmounted () {
        document.removeEventListener('click',this.handleClickOutside);
    }
    ,
    watch:{
        items: function(value) {
            if (this.isAsync){
                this.results=value;
                this.isOpen = true;
                this.isLoading = false;
            }
        },

    },

    methods: {
            handleClickOutside(event) {
                if(!this.$el.contains(event.target)) {
                    this.isOpen=false;
                    this.arrowCounter = -1;
                }
            },
            onArrowDown() {
                if (this.arrowCounter < this.results.length) {
                    this.arrowCounter = this.arrowCounter + 1;
                }
            },
            onArrowUp() {
                if (this.arrowCounter > 0) {
                    this.arrowCounter = this.arrowCounter -1;
                }
            },
            onEnter() {
                this.search = this.results[this.arrowCounter];
                this.arrowCounter= -1;
                this.isOpen=false;
            },
             filterResults() {
                this.results = this.items.filter(item => item.toLowerCase().indexOf(this.search.toLowerCase()) > -1);
             },
             onChange() {
                this.$emit('input', this.search);
                if (this.isAsync) {
                    this.isLoading = true;
                } else {
                    this.filterResults();
                    this.isOpen = true;
                }
                this.filterResults();
                this.isOpen = true;
                
            },
            setResult(result) {
                this.search = result;
                this.isOpen = false;
                this.$emit("selected",result)
            }
    }
};
</script>
<style>
    .autocomplete {
        position: relative;
    }

    .autocomplete-results {
        padding:0;
        margin: 0;
        border: 1px solid #eeeeee;
        height: 120px;
        min-height: 1em;
        max-height: 6em;
    }
    .autocomplete-result {
        list-style: none;
        text-align: left;
        padding:4px 2px;
        cursor: pointer;
    }
    .autocomplete-result.is-active,
    .autocomplete-result:hover {
        background-color: #4AAE9B;
        color: white;
    }
    
</style>