<template>
    <div class="carousel">
      <div
        v-for="(slide, index) in slides"
        :key="index"
        v-show="index === currentSlide"
        class="carousel-slide"
      >
        {{ slide }}
      </div>
      <button @click="prevSlide">Prev</button>
      <button @click="nextSlide">Next</button>
    </div>
  </template>
  
  <script>
  import { ref } from 'vue';
  
  export default {
    setup() {
      const currentSlide = ref(0);
      const slides = [
        'Slide 1',
        'Slide 2',
        'Slide 3',
        // ... more slides
      ];
  
      function nextSlide() {
        currentSlide.value = (currentSlide.value + 1) % slides.length;
      }
  
      function prevSlide() {
        currentSlide.value = (currentSlide.value - 1 + slides.length) % slides.length;
      }
  
      return {
        currentSlide,
        slides,
        nextSlide,
        prevSlide
      };
    }
  };
  </script>
  
  <style>
  .carousel-slide {
    position: absolute;
    width: 100%;
    transition: opacity 0.3s;
  }
  </style>