<template>
  <v-flex xs12>
      <v-breadcrumbs divider="/" class="pa-0">
          <v-icon slot="divider">chevron_right</v-icon>
          <!--<v-breadcrumbs-item>
              <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
          </v-breadcrumbs-item>-->
          <v-breadcrumbs-item to="/dich-vu" exact>Dịch vụ</v-breadcrumbs-item>
          <v-breadcrumbs-item :to="'/dich-vu/' + dichVu.dichVuID " exact>{{dichVu.tenDichVu}}</v-breadcrumbs-item>
      </v-breadcrumbs>
      <v-layout row wrap>
          <v-card style="width: 100%; margin-bottom: 20px">
              <v-layout row wrap style="padding: 15px">
                  <v-flex xs12 sm5>
                      <template>
                          <v-carousel  v-if="dichVu.media.length != 0" style="max-height: 400px">
                              <v-carousel-item v-for="(item,i) in dichVu.media"
                                               :key="i" style="max-height: 400px"
                                               :src="item.duongLink"
                                               aspect-ratio="1" class="grey lighten-2">
                              </v-carousel-item>
                          </v-carousel>
                          <v-carousel v-else style="max-height: 400px">
                              <v-carousel-item style="max-height: 400px"
                                               :src="dichVu.anhDaiDien"
                                               aspect-ratio="1" class="grey lighten-2">
                              </v-carousel-item>
                          </v-carousel>
                      </template>
                  </v-flex>
                  <v-flex xs12 sm7>
                      <v-card-text style="padding: 0px">
                          <v-card-title primary-title>
                              <h3 class="headline mb-0" style="font-size:30px !important">{{ dichVu.tenDichVu}}</h3>
                          </v-card-title>
                          <v-card-actions>
                              <v-layout row wrap>
                                  <v-flex xs12 style="padding-left: 0px">
                                      <v-btn color="primary" style="background-color:#f6821f; font-size:18px" class="ml-2" @click.native="dialogConfirm=true">
                                          Đặt dịch vụ
                                      </v-btn>
                                  </v-flex>
                                  <v-flex xs12 v-if="dichVu.moTaNgan != null">
                                      <div style="font-size: 20px;color: dimgrey;padding-left:4px">
                                          Mô tả ngắn: <br />
                                          <p>{{dichVu.moTaNgan}}</p>
                                      </div>
                                  </v-flex>

                              </v-layout>
                          </v-card-actions>
                      </v-card-text>
                  </v-flex>
              </v-layout>
          </v-card>
          <v-card style="width: 100%">
              <v-layout row wrap style="padding: 15px">
                  <v-flex xs12>
                      <div class="headerCT">MÔ TẢ DỊCH VỤ</div>
                  </v-flex>
                  <v-flex xs12>
                      <div class="_2aZyWI">
                          <div class="_2u0jt9">
                              <span v-html="dichVu.baiViet"></span>
                          </div>
                      </div>

                  </v-flex>
              </v-layout>
          </v-card>
          <v-dialog v-model="dialogConfirm" max-width="400">
                <v-card>
                    <v-card-title class="headline">Xác nhận</v-card-title>
                    <v-card-text class="pt-3">Bạn có chắc chắn muốn đặt dịch vụ?</v-card-text>
                    <v-card-text >
                        <v-layout>
                            <v-flex xs12>
                                <v-textarea rows="3" v-model="datDichVu.yeuCau"
                                            label="Yêu cầu khách hàng"
                                            :error-messages="errors.collect('Yêu cầu khách hàng', 'frmAddEdit')"
                                            v-validate="''"
                                            data-vv-scope="frmAddEdit"
                                            data-vv-name="Yêu cầu khách hàng"
                                            hide-details>
                                </v-textarea>
                            </v-flex>
                        </v-layout>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirm=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click="xacNhanDat" :loading="loading" :disabled="loading" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
        </v-dialog>
      </v-layout>
  </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DichVuApi, { DichVuApiSearchParams } from '@/apiResources/DichVuApi';
    import { DichVu } from '@/models/DichVu';
    import APIS from '@/apisServer';
    import { DatDichVu } from '@/models/DatDichVu';
    import DatDichVuApi, { DatDichVuApiSearchParams } from '@/apiResources/DatDichVuApi';

    export default Vue.extend({
        components: {},
        data() {
            return {
                datDichVu: {} as DatDichVu,
                date: new Date().toISOString().substr(0, 10),
                menu: false,
                isUpdate: false,
                dichVu: {} as DichVu,
                searchParamsDichVu: { includeEntities: true, rowsPerPage: 10 } as DichVuApiSearchParams,
                selectedDichVu: {} as DichVu,
                APIS: APIS,
                dialogConfirm: false,
                loading: false,
                dialog: false,
            }
        },
        mounted() {
            if (this.$route.name === 'ChiTietDichVu') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        watch: {
        },
        methods: {
            getDataFromApi(id: number): void {
                DichVuApi.detail(id).then(res => {
                    this.dichVu = res;
                });
            },
            xacNhanDat(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                    if (res) {
                        this.datDichVu.dichVuID = this.dichVu.dichVuID;
                        this.datDichVu.ngayDat = this.$moment();
                        this.datDichVu.userID = this.$store.state.user.User.userId;
                        this.loading = true;
                        DatDichVuApi.insert(this.datDichVu).then(res => {
                            this.dialogConfirm = false;
                            this.$emit("getData");
                            this.datDichVu = res;
                            this.isUpdate = true;
                            this.loading = false;
                            this.$snotify.success('Đặt dịch vụ thành công!');
                        }).catch(res => {
                            this.loading = false;
                            this.$snotify.error('Đặt dịch vụ thất bại!');
                        });
                    }
                });
            },
        }
    });
</script>
<style>
    ._2aZyWI {
        margin: 1.875rem .9375rem .9375rem;
    }

    .kIo6pj {
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        margin-bottom: 1.125rem;
    }

    ._2u0jt9 {
        white-space: pre-wrap;
        color: rgba(0,0,0,.8);
        font-size: 16px;
        overflow: hidden;
        text-overflow: ellipsis;
        line-height: 1.875rem;
    }

    ._1ymsZN {
        color: rgba(0,0,0,.4);
        font-size: 16px;
        width: 6.25rem;
        margin-right: 2.5rem;
    }

    ._1z1CEl {
        height: .9375rem;
    }

    .items-center {
        -webkit-box-align: center;
        -webkit-align-items: center;
        -moz-box-align: center;
        -ms-flex-align: center;
        align-items: center;
    }

    ._1FzU2Y .shopee-input-quantity {
        background: #fff;
    }

    .headerCT {
        background: rgba(0,0,0,.02);
        color: rgba(0,0,0,.87);
        font-size: 20px;
        padding: .875rem;
        text-transform: capitalize;
    }

    .shopee-input-quantity {
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -moz-box-align: center;
        -ms-flex-align: center;
        align-items: center;
    }

        .shopee-input-quantity > .shopee-button-outline:first-child {
            border-top-right-radius: 0;
            border-bottom-right-radius: 0;
        }

        .shopee-input-quantity .shopee-button-outline {
            width: 2rem;
            height: 2rem;
        }

    .shopee-button-outline {
        outline: none;
        cursor: pointer;
        border: none;
        font-size: .875rem;
        font-weight: 300;
        line-height: 1;
        letter-spacing: 0;
        display: -webkit-box;
        display: -webkit-flex;
        display: -moz-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -webkit-align-items: center;
        -moz-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        -webkit-box-pack: center;
        -webkit-justify-content: center;
        -moz-box-pack: center;
        -ms-flex-pack: center;
        justify-content: center;
        -webkit-transition: background-color .1s cubic-bezier(.4,0,.6,1);
        transition: background-color .1s cubic-bezier(.4,0,.6,1);
        border: 1px solid rgba(0,0,0,.09);
        border-radius: 2px;
        background: transparent;
        color: rgba(0,0,0,.8);
    }

    .shopee-input-quantity .shopee-button-outline-mid {
        width: 3.125rem;
        height: 2rem;
        border-left: none;
        border-right: none;
        font-size: 1rem;
        font-weight: 400;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        text-align: center;
        cursor: text;
        border-radius: 0;
    }

    button, html input[type=button], input[type=reset], input[type=submit] {
        -webkit-appearance: button;
        cursor: pointer;
    }

    button, select {
        text-transform: none;
    }

    button {
        overflow: visible;
    }

    button, input, optgroup, select, textarea {
        color: inherit;
        font: inherit;
        margin: 0;
    }

    button {
        cursor: pointer;
    }

    input[type=number]::-webkit-inner-spin-button,
    input[type=number]::-webkit-outer-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }
</style>