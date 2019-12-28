<template>
  <v-flex xs12>
      <v-breadcrumbs divider="/" class="pa-0">
          <v-icon slot="divider">chevron_right</v-icon>
          <!--<v-breadcrumbs-item>
              <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
          </v-breadcrumbs-item>-->
          <v-breadcrumbs-item to="/dich-vu" exact>Dịch vụ</v-breadcrumbs-item>
      </v-breadcrumbs>
    <v-layout row wrap>
        <v-flex xs12 md3>
            <v-text-field label="Tìm kiếm dịch vụ" hide-details v-model="searchParamsDichVu.tenDichVu" @input="getDataFromApi(searchParamsDichVu)"></v-text-field>
        </v-flex>
    </v-layout>
    <v-layout row wrap>
        <template v-for="item in dsDichVu">
            <v-flex xs12 md6>
                <v-card style="margin: 5px;" :to="'/dich-vu/' + item.dichVuID">
                    <v-layout nowrap>
                        <v-flex xs4>
                            <v-img v-if="item.anhDaiDien != null" slot="activator"
                                   :src="APIS.HOST + 'fileupload/download?key=' + item.anhDaiDien"
                                   style="max-width: 100%;" id="img"
                                   aspect-ratio="1"
                                   class="grey lighten-2">
                                <template v-slot:placeholder>
                                    <v-layout fill-height
                                              align-center
                                              justify-center
                                              ma-0>
                                        <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                    </v-layout>
                                </template>
                            </v-img>
                            <v-img v-else src='/static/images/bao-tri-dieu-hoa-trung-tam.jpg' slot="activator"
                                   style="max-width: 100%;" id="img"
                                   aspect-ratio="1"
                                   class="grey lighten-2">
                                <template v-slot:placeholder>
                                    <v-layout fill-height
                                              align-center
                                              justify-center
                                              ma-0>
                                        <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                    </v-layout>
                                </template>
                            </v-img>
                        </v-flex>
                        <v-flex xs8>
                            <v-card-title primary-title style="padding-top: 0px!important">
                                <div>
                                    <div class="headline">{{item.tenDichVu}}</div>
                                    <p style=" margin-bottom: 0px;text-overflow: ellipsis; overflow: hidden; height: 81px">{{item.moTaNgan}}</p>
                                </div>
                            </v-card-title>
                        </v-flex>
                    </v-layout>
                </v-card>
            </v-flex>
        </template>
    </v-layout>
  </v-flex>
</template>
<script lang="ts">
    import APIS from '@/apisServer';
    import { Vue } from 'vue-property-decorator';
    import DichVuApi, { DichVuApiSearchParams } from '@/apiResources/DichVuApi';
    import { DichVu } from '@/models/DichVu';
    APIS: APIS
    export default Vue.extend({
        components: {},
        data() {
            return {
                dsDichVu: [] as DichVu[],
                searchParamsDichVu: { } as DichVuApiSearchParams,
                selectedDichVu: {} as DichVu,
                dialogConfirmDelete: false,
                APIS: APIS
            }
        },
        watch: {
        },
        created() {
            this.getDataFromApi(this.searchParamsDichVu);
        },
        methods: {
            getDataFromApi(searchParamsDichVu: DichVuApiSearchParams): void {
                searchParamsDichVu.trangThai = true;
                DichVuApi.search(searchParamsDichVu).then(res => {
                    this.dsDichVu = res as any;
                    //this.searchParamsDichVu.totalItems = res.pagination.totalItems;
                });
            }
        }
    });
</script>
<style>
    .headline {
        font-size: 20px !important;
        line-height: 30px !important;
    }
    .v-card {
        box-shadow: 0 0px 0px -1px rgba(0,0,0,.2), 0 1px 1px 0 rgba(0,0,0,.14), 0 1px 1px 0 rgba(0,0,0,.12);
        text-decoration: none;
    }
</style>