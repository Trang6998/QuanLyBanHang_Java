import Vue from 'vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

import colors from 'vuetify/es5/util/colors'
import viVee from 'vee-validate/dist/locale/vi';
import VeeValidate, { Validator } from 'vee-validate';
import Vue2Filters from 'vue2-filters';
import money from 'v-money'
// register directive v-money and component <money>
Vue.use(money, { precision: 0 })
Vue.use(Vue2Filters);
Vue.use(VeeValidate);
Validator.localize('vi', viVee);

Vue.use(Vuetify, {
    theme: {
      primary: colors.yellow.darken4, // #E53935
      secondary: colors.red.lighten4, // #FFCDD2
      accent: colors.indigo.base // #3F51B5
    }
})
