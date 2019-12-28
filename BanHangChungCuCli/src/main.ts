import '@babel/polyfill'
import Vue from 'vue';
import './plugins/vuetify'
import App from './App.vue';
import router from './router';
import './registerServiceWorker';
import { Validator } from "vee-validate";
import store from './store/store';
import Snotify from 'vue-snotify';
import { SnotifyService } from 'vue-snotify/SnotifyService';
import DateTimePicker from '@/components/Commons/DateTimePicker';
import CommonComponents from '@/components/Commons';
import EventBus from '@/EventBus';
//import CommonFunctions, { CommonFunctionsService } from './Utils/CommonFunctions';
import * as moment from 'moment';
Vue.use(DateTimePicker);
Vue.use(CommonComponents);

require('moment/locale/vi');

Vue.use(require('vue-moment'), {
    moment
});

// Vue.use(VeeValidate);
Vue.use(Snotify);
Vue.use(EventBus);
Vue.config.productionTip = false;

declare module 'vue/types/vue' {
    interface Vue {
        $snotify: SnotifyService,
        $moment: any,
        $validator: Validator,
        $eventBus: Vue,
    }
}
new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount('#app');
