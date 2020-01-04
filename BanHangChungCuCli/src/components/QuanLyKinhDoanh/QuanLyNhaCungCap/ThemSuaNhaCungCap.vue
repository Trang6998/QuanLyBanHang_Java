<template>
    <v-dialog v-model="dialog" width="600" persistent scrollable>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>{{isUpdate ? 'Cập nhật nhà cung cấp' : 'Thêm mới nhà cung cấp'}}</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text>
                    <v-form scope="frmAddEdit">
                        <v-layout row wrap>
                        
                            <v-flex xs12>
                                <v-text-field v-model="nhaCungCap.tenNhaCungCap"
                                              label="Tên nhà cung cấp"
                                              type="text"
                                              :error-messages="errors.collect('Tên nhà cung cấp', 'frmAddEdit')"
                                              v-validate="'required'"
                                              data-vv-scope="frmAddEdit"
                                              data-vv-name="Tên nhà cung cấp"
                                              clearable></v-text-field>
                            </v-flex>

                            <v-flex xs6>
                                <v-text-field v-model="nhaCungCap.soDienThoai"
                                              label="Số điện thoại"
                                              type="text"
                                              :error-messages="errors.collect('Số điện thoại', 'frmAddEdit')"
                                              v-validate="''"
                                              data-vv-scope="frmAddEdit"
                                              data-vv-name="Số điện thoại"
                                              clearable></v-text-field>
                            </v-flex>

                            <v-flex xs6>
                                <v-text-field v-model="nhaCungCap.email"
                                              label="Email"
                                              type="text"
                                              :error-messages="errors.collect('Email', 'frmAddEdit')"
                                              v-validate="''"
                                              data-vv-scope="frmAddEdit"
                                              data-vv-name="Email"
                                              clearable></v-text-field>
                            </v-flex>
                            <v-flex xs12>
                                <v-text-field v-model="nhaCungCap.diaChi"
                                              label="Địa chỉ"
                                              type="text"
                                              :error-messages="errors.collect('Địa chỉ', 'frmAddEdit')"
                                              v-validate="''"
                                              data-vv-scope="frmAddEdit"
                                              data-vv-name="Địa chỉ"
                                              clearable></v-text-field>
                            </v-flex>


                            <v-flex xs12>
                                <v-textarea rows="2" v-model="nhaCungCap.ghiChu"
                                            label="Ghi chú"
                                            type="text"
                                            :error-messages="errors.collect('Ghi chú', 'frmAddEdit')"
                                            v-validate="''"
                                            data-vv-scope="frmAddEdit"
                                            data-vv-name="Ghi chú"
                                            hide-details>
                                </v-textarea>
                            </v-flex>
                            <v-flex xs12>
                                <v-layout row wrap>
                                    <v-flex xs12>
                                        <h3>Danh sách sản phẩm cung cấp: </h3>
                                    </v-flex>
                                    <v-flex xs12 sm6 v-for="sp in dsSanPhamNhaCungCap" :key="sp">
                                        <span>* {{sp.tenSanPham}}</span>
                                    </v-flex>
                                </v-layout>
                            </v-flex>
                        </v-layout>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn class="primary" small :disabled="loading" :loading="loading" @click.native="save">{{isUpdate?'Cập nhật':'Thêm mới'}}</v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import NhaCungCapApi, { NhaCungCapApiSearchParams } from '@/apiResources/NhaCungCapApi';
    import { NhaCungCap } from '@/models/NhaCungCap';
    import { SanPham } from '@/models/SanPham';
    import SanPhamNhaCungCapApi, { SanPhamNhaCungCapApiSearchParams } from '@/apiResources/SanPhamNhaCungCapApi';
import SanPhamApi, { SanPhamApiSearchParams } from '../../../apiResources/SanPhamApi';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                nhaCungCap: {} as NhaCungCap,
                nhaCungCapID: 0,
                dsSanPhamNhaCungCap: [] as SanPham[],
                dsSanPhamNhaCungCapLoading: false,
                searchSanPhamNhaCungCap: '',
                dialog: false,
                loading: false,
                searchParamsNhaCungCap: {} as NhaCungCapApiSearchParams,
                searchParamsSanPham: {} as SanPhamApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaNhaCungCap') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            show(isUpdate: boolean, item: NhaCungCap) {
                this.dialog = true;
                this.$validator.errors.clear("frmAddEdit");
                this.isUpdate = isUpdate;

                if (this.isUpdate !== true) {
                    this.nhaCungCap = {} as NhaCungCap;
                }
                else {
                    this.nhaCungCapID = item.nhaCungCapID;
                    this.getDataFromApi(this.nhaCungCapID);
                    this.getDsSanPham(this.nhaCungCapID);
                }
            },
            hide() {
                this.dialog = false;
            },
            getDataFromApi(id: number): void {
                NhaCungCapApi.detail(id).then(res => {
                    this.nhaCungCap = res;
                });
            },
            getDsSanPham(id: number){
               this.searchParamsSanPham.nhaCungCapID = id;
               SanPhamApi.search(this.searchParamsSanPham).then(res =>{
                   this.dsSanPhamNhaCungCap = res as any
               })
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.nhaCungCap.sanPhamNhaCungCap = undefined;
                        if (this.isUpdate) {
                            this.loading = true;
                            NhaCungCapApi.update(this.nhaCungCapID, this.nhaCungCap).then(res => {
                                this.loading = false;
                                this.$emit("getData");
                                this.dialog = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            NhaCungCapApi.insert(this.nhaCungCap).then(res => {
                                this.$emit("getData");
                                this.dialog = false;
                                this.nhaCungCap = res;
                                this.isUpdate = true;
                                this.loading = false;
                                this.$snotify.success('Thêm mới thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Thêm mới thất bại!');
                            });
                        }
                    }
                });
            },
        }
    });
</script>

