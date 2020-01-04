<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12>
                <v-layout row wrap>
                    <v-flex xs12>
                        <h3>Danh sách dịch vụ</h3>
                    </v-flex>
                    <v-flex xs12>
                        <v-layout nowrap>
                            <v-flex xs6>
                                <v-text-field label="Tìm kiếm" ma-0 pa-0 hide-details v-model="searchParamsDichVu.tenDichVu" @input="getDataFromApi(searchParamsDichVu)"></v-text-field>
                            </v-flex>
                            <v-spacer></v-spacer>
                                    <v-btn small @click="showModalThemSuaDichVu(false,{anhDaiDien: null})" color="primary">Thêm dịch vụ</v-btn>
                                
                        </v-layout>
                    </v-flex>
                    <v-flex xs12>
                        <v-data-table :headers="tableHeader"
                                      :items="dsDichVu"
                                      hide-actions :pagination.sync="searchParamsDichVu"
                                      :loading="loadingTable"
                                      class="table-border table">
                            <template slot="items" slot-scope="props">
                                <td class="text-xs-center">{{ props.index + 1 }}</td>
                                <td>{{ props.item.tenDichVu }}</td>
                                <td>{{ props.item.moTaNgan }}</td>
                                <td class="text-xs-center"><a @click="updateTrangThai(props.item)">{{ props.item.trangThai ? "Ẩn" : "Hiện" }}</a></td>
                                <td class="text-xs-center text-nowrap" style="width:100px;">
                                    <v-btn icon small class="ma-0" @click="showModalThemSuaDichVu(true,props.item)">
                                        <v-icon small color="teal">edit</v-icon>
                                    </v-btn>
                                    <v-btn icon small class="ma-0" @click="confirmDelete(props.item)">
                                        <v-icon small color="red">delete</v-icon>
                                    </v-btn>
                                </td>
                            </template>
                            <template slot="no-data">
                                <p class="text-xs-center mb-0">Không có dữ liệu</p>
                            </template>
                        </v-data-table>
                        <div class="text-xs-center pt-2 xxx">
                            <v-pagination :total-visible="5"  v-model="page" :length="pages"></v-pagination>
                        </div>
                    </v-flex>
                </v-layout>
            </v-flex>
            <them-sua-dich-vu ref="themSuaDichVu" @getData="getDataFromApi(searchParamsDichVu)"></them-sua-dich-vu>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">
                        Bạn có chắc chắn muốn xóa dịch vụ này?
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteDichVu" flat>Xác Nhận</v-btn>
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
    import ThemSuaDichVu from './ThemSuaDichVu.vue';
    import { HTTP } from '@/http-servicesNew';
    export default Vue.extend({
        components: { ThemSuaDichVu},
        data() {
            return {
                dsDichVu: [] as DichVu[],
                tableHeader: [
                    { text: '#', value: '#', align: 'center', sortable: false },
                    { text: 'Tên dịch vụ', value: 'tenDichVu', align: 'center', sortable: false },
                    { text: 'Mô tả ngắn', value: 'moTaNgan', align: 'center', sortable: false },
                    { text: 'Ẩn/Hiện', value: 'trangThai', align: 'center', sortable: false },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsDichVu: { rowsPerPage: 5 } as DichVuApiSearchParams,
                loadingTable: false,
                selectedDichVu: {} as DichVu,
                dialogConfirmDelete: false,
                dsToaNha: [] as any,
                page: 1
            }
        },
        watch: {
            page: function () {
                this.searchParamsDichVu.page = this.page;
            }
        },
        computed: {
            pages(): number {
                if (this.searchParamsDichVu.rowsPerPage == null ||
                    this.searchParamsDichVu.totalItems == null
                )
                     return 0;
                return Math.ceil(this.searchParamsDichVu.totalItems / this.searchParamsDichVu.rowsPerPage);
            }
        },
        created() {
            //this.getDanhSachToaNha();
            this.getDataFromApi(this.searchParamsDichVu)
        },
        methods: {
            getDataFromApi(searchParamsDichVu: DichVuApiSearchParams): void {
                this.loadingTable = true;
                DichVuApi.search(searchParamsDichVu).then(res => {
                    this.dsDichVu = res as any;
                    this.searchParamsDichVu.totalItems = (res as any).length;
                    //this.searchParamsDichVu.page = (res.pagination.page as any) + 1;
                    this.loadingTable = false;
                });
            },
            showModalThemSuaDichVu(isUpdate: boolean, item: DichVu) {
                (this.$refs.themSuaDichVu as any).show(isUpdate, item);
            },
            updateTrangThai(dichVu: DichVu) {
                if (dichVu.trangThai === true) dichVu.trangThai = false;
                else dichVu.trangThai = true;
                this.loadingTable = true;
                DichVuApi.update(dichVu.dichVuID, dichVu).then(res => {
                    this.loadingTable = false;
                    this.getDataFromApi(this.searchParamsDichVu);
                }).catch(res => {
                    this.loadingTable = false;
                });
            },
            confirmDelete(dichVu: DichVu): void {
                this.selectedDichVu = dichVu;
                this.dialogConfirmDelete = true;
            },
            deleteDichVu(): void {
                DichVuApi.delete(this.selectedDichVu.dichVuID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsDichVu);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error(res.response.data.message);
                });
            },
            getDanhSachToaNha() {
                HTTP.get('odata/ToaNha').then(res => {
                    this.dsToaNha.push({
                        ToaNhaId: null as any,
                        TenToaNha: 'Tất cả'
                    } as any);
                    this.dsToaNha.push(...res.data.value);
                    this.searchParamsDichVu.toaNhaID = this.$store.state.user.User.ToaNhaId;
                    this.getDataFromApi(this.searchParamsDichVu);
                })
            },
        }
    });
</script>
<style>
    .v-input--selection-controls {
        margin-top: 0px;
        padding-top: 0px;
    }

    .v-input {
        -webkit-box-align: start;
        -ms-flex-align: start;
        align-items: flex-start;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-flex: 1;
        -ms-flex: 1 1 auto;
        flex: 1 1 auto;
        padding-top: 0px;
        text-align: left;
    }

        .v-input .v-label {
            height: 20px;
            line-height: 20px;
            font-size: 14px;
        }
</style>
