export default {
  // Disable server-side rendering: https://go.nuxtjs.dev/ssr-mode
  ssr: false,

  // Target: https://go.nuxtjs.dev/config-target
  target: 'static',

  // env variables loaded accessible on the front-end
  publicRuntimeConfig: {
    apiHost:process.env.API_HOST,
  },

  // env variables loaded accessible on the server side
  privateRuntimeConfig: {

  },

  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'console',
    htmlAttrs: {
      lang: 'en',
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
      { name: 'format-detection', content: 'telephone=no' },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    // https://go.nuxtjs.dev/typescript
    '@nuxt/typescript-build',
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    // https://go.nuxtjs.dev/bootstrap
    'bootstrap-vue/nuxt',
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    // ???
    '@nuxtjs/auth-next'
  ],

  // Axios module configuration: https://go.nuxtjs.dev/config-axios
  axios: {
    // Workaround to avoid enforcing hard-coded localhost:3000: https://github.com/nuxt-community/axios-module/issues/308
    baseURL: '/',
  },

  // router
  router: {
    middleware: ['auth']
  },

  // Authentication configuration to use JWT during authentication
  auth: {
    strategies: {
      local: {
        token: {
          property: 'chirpstackBearer',
          global: true,
          required: true,
          type: 'Bearer'
        },
        user: {
          property: 'user',
          autoFetch: false
        },
        chirpstackBearer: {
          property: 'chirpstackBearer',
          global: true,
          required: true,
        },
        endpoints: {
          login: { url: process.env.API_HOST+'/console/1.0/sign/in', method: 'post' },
          logout: { url: process.env.API_HOST+'/console/1.0/sign/out', method: 'get' },
          user: { url: process.env.API_HOST+'/console/1.0/user', method: 'get' }
        },
        redirect: {
          login: '/login',
          logout: '/',
          callback: '/login',
          home: '/'
        }
      }
    },
  },
  

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {},
}