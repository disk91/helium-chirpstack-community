export default {
  // Disable server-side rendering: https://go.nuxtjs.dev/ssr-mode
  ssr: false,

  // Target: https://go.nuxtjs.dev/config-target
  target: 'static',

  // env variables loaded accessible on the front-end
  publicRuntimeConfig: {
    apiHost:process.env.API_HOST || '',
    chirpstackHost:(process.env.CHIRPSTACK_HOST || '' )+'/' || '/',
    consoleName:process.env.CONSOLE_NAME || 'HeliumConsole',
    dcbalanceEndpoint:(process.env.API_HOST || '')+'/console/1.0/tenant/balance',
    signupEndpoint:(process.env.API_HOST || '')+'/console/1.0/sign/up',
    validationEndpoint:(process.env.API_HOST || '')+'/console/1.0/sign/confirm',
    passLostEndpoint:(process.env.API_HOST || '')+'/console/1.0/sign/lost',
    passChangeEndpoint:(process.env.API_HOST || '')+'/console/1.0/sign/password',
    addTenantEndpoint:(process.env.API_HOST || '')+'/console/1.0/tenant/create',
    tenantSetupGet:(process.env.API_HOST || '')+'/console/1.0/tenant/setup',
    tenantSetupGetDetails:(process.env.API_HOST || '')+'/console/1.0/tenant/setup/detail',
    tenantSetupCreate:(process.env.API_HOST || '')+'/console/1.0/tenant/setup',
    tenantSetupTemplateList:(process.env.API_HOST || '')+'/console/1.0/tenant/setup/templates',
    tenantSetupUpdate:(process.env.API_HOST || '')+'/console/1.0/tenant/setup',
    tenantSetupDelete:(process.env.API_HOST || '')+'/console/1.0/tenant/setup',
    tenantSearch:(process.env.API_HOST || '')+'/console/1.0/tenant/search',
    tenantDcUpdate:(process.env.API_HOST || '')+'/console/1.0/tenant/balance',
    tenantDcTrans:(process.env.API_HOST || '')+'/console/1.0/tenant/dc/transfer',
    tenantsDcGet:(process.env.API_HOST || '')+'/console/1.0/tenant/balance',
    tenantBasicStat:(process.env.API_HOST || '')+'/console/1.0/tenant',
    tenantKeyCreate:(process.env.API_HOST || '')+'/console/1.0/tenant/key',
    tenantKeyDelete:(process.env.API_HOST || '')+'/console/1.0/tenant/key',
    tenantMaxCopyUpdate:(process.env.API_HOST || '')+'/console/1.0/tenant/maxcopy',
    messageAdmGet:(process.env.API_HOST || '')+'/console/1.0/message/admin',
    messageAdmUpd:(process.env.API_HOST || '')+'/console/1.0/message/admin',
    messageAdmNew:(process.env.API_HOST || '')+'/console/1.0/message/admin',
    messageAdmDel:(process.env.API_HOST || '')+'/console/1.0/message/admin',
    messageGet:(process.env.API_HOST || '')+'/console/1.0/message/',
    messageAck:(process.env.API_HOST || '')+'/console/1.0/message/',
    messagePublicGet:(process.env.API_HOST || '')+'/console/1.0/message/public',
    userDetailsGet:(process.env.API_HOST || '')+'/console/1.0/user/details',
    userDetailsUpd:(process.env.API_HOST || '')+'/console/1.0/user/details',
    userListGet:(process.env.API_HOST || '')+'/console/1.0/user/list',
    userStatusGet:(process.env.API_HOST || '')+'/console/1.0/user/',
    userBanPut:(process.env.API_HOST || '')+'/console/1.0/user/ban',
    transactionListGet:(process.env.API_HOST || '')+'/console/1.0/transaction/',
    transactionStripeCreate:(process.env.API_HOST || '')+'/console/1.0/transaction/intent',
    transactionStripeUpdate:(process.env.API_HOST || '')+'/console/1.0/transaction/intent',
    transactionSetup:(process.env.API_HOST || '')+'/console/1.0/transaction/setup',
    transactionInvoice:(process.env.API_HOST || '')+'/console/1.0/transaction/invoice',
    transactionAdmList:(process.env.API_HOST || '')+'/console/1.0/transaction/completed',
    backVersionGet:(process.env.API_HOST || '')+'/console/1.0/misc/version',
    statusGet:(process.env.API_HOST || '')+'/console/1.0/misc/status',
    invoiceSetupGet:(process.env.API_HOST || '')+'/console/1.0/invoice/setup',
    invoiceSetupUpd:(process.env.API_HOST || '')+'/console/1.0/invoice/setup',
    ticketListGet:(process.env.API_HOST || '')+'/console/1.0/ticket/',
    ticketDetailGet:(process.env.API_HOST || '')+'/console/1.0/ticket',
    ticketCreatePost:(process.env.API_HOST || '')+'/console/1.0/ticket/',
    ticketCreateResponsePut:(process.env.API_HOST || '')+'/console/1.0/ticket/',
    ticketCountPendingGet:(process.env.API_HOST || '')+'/console/1.0/ticket/count/',
    proxyGet:(process.env.API_HOST || '')+'/console/1.0/proxy/getter',
    proxyDeact:(process.env.API_HOST || '')+'/console/1.0/proxy/deactivate',
    ouiGet:(process.env.API_HOST || '')+'/console/1.0/misc/oui',
    conditionGet:(process.env.API_HOST || '')+'/console/1.0/misc/condition',
    conditionPut:(process.env.API_HOST || '')+'/console/1.0/misc/condition',
    userConditionPut:(process.env.API_HOST || '')+'/console/1.0/user/condition',
    couponPost:(process.env.API_HOST || '')+'/console/1.0/tenant/coupon',
    couponGet:(process.env.API_HOST || '')+'/console/1.0/tenant/coupon',
    termAndUse:process.env.CONSOLE_TERMS || '/',
    maxDevices:process.env.MAX_DEVICES || 500,
    disablePurchase:process.env.DIS_DC_PURCHASE || 'false',
    disableNewTenant:process.env.DIS_NEW_TENANT || 'false',
    disableStatTenant:process.env.DIS_STAT_TENANT || 'false',
    frontVersion:'1.7.0'
  },

  // env variables loaded accessible on the server side
  privateRuntimeConfig: {

  },

  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: (process.env.CONSOLE_NAME || 'Helium Console'),
    htmlAttrs: {
      lang: 'en',
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
      { name: 'format-detection', content: 'telephone=no' },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/static/front/favicon.ico' }],
    script: [
      { src: 'https://js.stripe.com/v3/' },
      { src: 'https://polyfill.io/v3/polyfill.min.js?version=3.52.1&features=fetch'}
    ],
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

  bootstrapVue: {
    icons: true
  },

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    // https://go.nuxtjs.dev/bootstrap
    'bootstrap-vue/nuxt',
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    // authentication
    '@nuxtjs/auth-next',
    // internationalization
    '@nuxtjs/i18n',
    // simple-code-editor
    'simple-code-editor/nuxt',
  ],

  // Internationalization
  i18n: {
    locales: [
      { code: 'en', iso: 'en-US', file: 'en.json', dir: 'ltr'},
      { code: 'fr', iso: 'fr-FR', file: 'fr.json', dir: 'ltr'},
    ],
    defaultLocale: 'en',
    detectBrowserLanguage: {
      useCookie: false
    },
    langDir: '~/front_locales/'
  },

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
    redirect: {
      login: '/front/login',
      logout: '/front/login',
      callback: false,
      home: '/front/'
    },
    strategies: {
      local: {
        token: {
          property: 'consoleBearer',
          global: true,
          required: true,
          type: 'Bearer'
        },
        user: {
          property: '',
          autoFetch: true
        },
        chirpstackBearer: {
          property: 'chirpstackBearer',
          global: true,
          required: true,
        },
        endpoints: {
          login: { url: (process.env.API_HOST || '')+'/console/1.0/sign/in', method: 'post' },
          logout: { url: (process.env.API_HOST || '')+'/console/1.0/sign/out', method: 'get' },
          user: { url: (process.env.API_HOST || '')+'/console/1.0/user/', method: 'get' }
        },
      }
    },
  },
  

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {},
}