<template>
  <div v-if="show">
    <button @click="closeShow"></button>
  </div>
</template>

<script>
export default {
  name: 'CreateRoom',
  data () {
    return {
    }
  },
  props: ['show'],
  methods: {
    methods: {
      closeShow () {
        this.$emit('update:show', false)
      }
    }
  }
}
</script>

<style scoped>

</style>
