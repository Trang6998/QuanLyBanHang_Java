<template>
    <v-flex xs12>
        <v-breadcrumbs divider="/" class="pa-0">
            <v-icon slot="divider">chevron_right</v-icon>
            <v-breadcrumbs-item>
                <v-btn flat class="ma-0" @click="$router.go(-1)" small><v-icon>arrow_back</v-icon> Quay lại</v-btn>
            </v-breadcrumbs-item>
            <v-breadcrumbs-item to="/datdichvu" exact>DatDichVu</v-breadcrumbs-item>
            <v-breadcrumbs-item>{{isUpdate?'Cập nhật':'Thêm mới'}}</v-breadcrumbs-item>
        </v-breadcrumbs>
        <v-card>
            <v-card-text>
                <v-form scope="frmAddEdit">
                    <v-layout row wrap>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="datDichVu.UserID"
                            :items="dsUsers"
                            :loading="dsUsersLoading"
                            :search-input.sync="searchUsers"
                            item-text="UserId"
                            item-value="UserId"
                            label="UserID"
                            placeholder="Nhập UserID"
                            :error-messages="errors.collect('UserID', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="UserID"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-autocomplete v-model="datDichVu.DichVuID"
                            :items="dsDichVu"
                            :loading="dsDichVuLoading"
                            :search-input.sync="searchDichVu"
                            item-text="DichVuID"
                            item-value="DichVuID"
                            label="DichVuID"
                            placeholder="Nhập DichVuID"
                            :error-messages="errors.collect('DichVuID', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="DichVuID"
                            clearable
                            ></v-autocomplete>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="datDichVu.NgayDat"
                            label="NgayDat"
                            type="datetime-local"
                            :error-messages="errors.collect('NgayDat', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="NgayDat"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6 sm4 md3>
                            <v-text-field
                            v-model="datDichVu.GhiChu"
                            label="GhiChu"
                            type="text"
                            :error-messages="errors.collect('GhiChu', 'frmAddEdit')"
                            v-validate="''"
                            data-vv-scope="frmAddEdit"
                            data-vv-name="GhiChu"
                            hide-details
                            clearable
                            ></v-text-field>
                        </v-flex>

                    </v-layout>
                </v-form>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn class="primary" :disabled="loading" :loading="loading" @click.native="save">{{isUpdate?'Cập nhật':'Thêm mới'}}</v-btn>
            </v-card-actions>
        </v-card>
    </v-flex>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DatDichVuApi, { DatDichVuApiSearchParams } from '@/apiResources/DatDichVuApi';
    import { DatDichVu } from '@/models/DatDichVu';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                datDichVu: {} as DatDichVu,
                loading: false,
                searchParamsDatDichVu: {} as DatDichVuApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
            if (this.$route.name === 'suaDatDichVu') {
                this.isUpdate = true;
                let id = parseInt(this.$route.params.id, 10);
                this.getDataFromApi(id);
            } else {
                this.isUpdate = false;
            }
        },
        methods: {
            getDataFromApi(id: number): void {
                DatDichVuApi.detail(id).then(res => {
                    this.datDichVu = res;
                });
            },
            save(): void {
                this.$validator.validateAll('frmAddEdit').then((res) => {
                   if (res) {
                        this.datDichVu.dichVu = undefined;
                        this.datDichVu.users = undefined;
                        if (this.isUpdate) {
                            let id = parseInt(this.$route.params.id, 10);
                            this.loading = true;
                            DatDichVuApi.update(id, this.datDichVu).then(res => {
                                this.loading = false;
                                this.$snotify.success('Cập nhật thành công!');
                            }).catch(res => {
                                this.loading = false;
                                this.$snotify.error('Cập nhật thất bại!');
                            });
                        } else {
                            this.loading = true;
                            DatDichVuApi.insert(this.datDichVu).then(res => {
                                this.$router.push('/datdichvu/' + res.datDichVuID);
                                this.datDichVu = res;
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

