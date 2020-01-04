<template>
    <v-flex xs12>
        <v-layout row wrap>
            <v-flex xs12><h3>Danh sách khách hàng đặt dịch vụ</h3></v-flex>
            <v-flex xs12 md4>
                <v-text-field label="Tìm kiếm" v-model="searchParamsDatDichVu.tenDichVu" @input="getDataFromApi(searchParamsDatDichVu)"></v-text-field>
            </v-flex>
            <v-flex xs6 md4>
                <v-datetimepicker label="Từ ngày" v-model="searchParamsDatDichVu.tuNgay"
                              :max="new Date()" @input="getDataFromApi(searchParamsDatDichVu)"></v-datetimepicker>
            </v-flex>
            <v-flex xs6 md4>
                <v-datetimepicker label="Đến ngày" v-model="searchParamsDatDichVu.denNgay"
                              :min="searchParamsDatDichVu.tuNgay" @input="getDataFromApi(searchParamsDatDichVu)"></v-datetimepicker>
            </v-flex>
           
            <v-flex xs12>
                <v-data-table :headers="tableHeader"
                              :items="dsDatDichVu"
                              hide-actions :pagination.sync="searchParamsDatDichVu"
                              :loading="loadingTable"
                              class="table-border table">
                    <template slot="items" slot-scope="props">
                        <td>{{ props.index + 1 }}</td>
                        <td>
                            <span style="white-space: nowrap">{{ props.item.users ? props.item.users.hoTen : '' }}</span>
                            <br />
                            <small style="white-space: nowrap" v-if="props.item.ngayDat != null ">
                                Ngày đăt: {{ props.item.ngayDat === null ? "" : props.item.ngayDat|moment('DD/MM/YYYY HH:mm') }}
                            </small>
                        </td>
                        <td>{{ props.item.tenPhong }}</td>
                        <td>{{ props.item.users ? props.item.users.soDienThoai : '' }}</td>
                        <td>{{ props.item.tenDichVu }}</td>
                        <td>{{ props.item.yeuCau }}</td>
                        <td>{{ props.item.ghiChu }}</td>
                        <td class="text-xs-center text-nowrap" style="width:100px;">
                            <v-btn flat small color="primary" @click="showGhiNhan(props.item)" class="ma-0">
                                Ghi nhận
                            </v-btn>
                            <v-btn flat small color="primary" @click="confirmDelete(props.item)" class="ma-0">
                                Xóa
                            </v-btn>
                        </td>
                    </template>
                    <template slot="no-data">
                        <p class="text-xs-center mb-0">Không có dữ liệu</p>
                    </template>

                </v-data-table>
                <div class="text-xs-center pt-2 xxx">
                    <v-pagination :total-visible="5" v-model="page" :length="pages"></v-pagination>
                </div>
            </v-flex>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteDatDichVu" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <v-dialog v-model="dialog" max-width="400">
                <v-card>
                    <v-card-title class="headline">Ghi nhận yêu cầu</v-card-title>
                    <v-card-text>
                        <v-flex xs12>
                            <v-textarea rows="3" v-model="datDichVu.ghiChu"
                                        placeholder="Ghi chú"
                                        label="Ghi chú"
                                        :error-messages="errors.collect('Ghi chú', 'frmAddEdit')"
                                        v-validate="''"
                                        data-vv-scope="frmAddEdit"
                                        data-vv-name="Ghi chú"
                                        hide-details>
                            </v-textarea>
                        </v-flex>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialog=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="ghiNhan" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-layout>
        
    </v-flex>
</template>
<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DatDichVuApi, { DatDichVuApiSearchParams } from '@/apiResources/DatDichVuApi';
    import { DatDichVu } from '@/models/DatDichVu';
    import { HTTP } from '@/http-servicesNew';
    import APIS from '@/apisServer';
    export default Vue.extend({
        components: {},
        data() {
            return {
                dialog: false,
                dsDatDichVu: [] as DatDichVu[],
                tableHeader: [
                    { text: '#', value: '#', align: 'center', sortable: false },
                    { text: 'Họ và tên', value: 'tenChuHo', align: 'center', sortable: true },
                    { text: 'Phòng', value: 'tenPhong', align: 'center', sortable: true },
                    { text: 'SĐT', value: 'soDienThoai', align: 'center', sortable: true },
                    { text: 'Dịch vụ', value: 'tenDichVu', align: 'center', sortable: true },
                    { text: 'Yêu cầu', value: 'yeuCau', align: 'center', sortable: true },
                    { text: 'Ghi chú', value: 'ghiChu', align: 'center', sortable: true },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
                ],
                searchParamsDatDichVu: { tenDichVu: '', tuNgay: null as any, denNgay: null as any, includeEntities: true, rowsPerPage: 10 } as DatDichVuApiSearchParams,
                loadingTable: false,
                selectedDatDichVu: {} as DatDichVu,
                dialogConfirmDelete: false,
                dsToaNha: [] as any,
                datDichVu: {} as DatDichVu,
                APIS: APIS,
                excelOn: false,
                interval: {} as any,
                value: 0,
                page: 1
            }
        },
        watch: {
            beforeDestroy() {
                clearInterval(this.interval)
            },
      
            page: function () {
                this.searchParamsDatDichVu.page = this.page;
            }
        },
        computed: {
            pages(): number {
                if (this.searchParamsDatDichVu.rowsPerPage == null ||
                    this.searchParamsDatDichVu.totalItems == null
                )
                    return 0;
                return Math.ceil(this.searchParamsDatDichVu.totalItems / this.searchParamsDatDichVu.rowsPerPage);
            }
        },
        created() {
            this.searchParamsDatDichVu.tuNgay = this.$moment().startOf('month');
            this.searchParamsDatDichVu.denNgay = this.$moment().endOf('month');
            //this.getDanhSachToaNha();
        },
        methods: {
            getDataFromApi(searchParamsDatDichVu: DatDichVuApiSearchParams): void {
                this.loadingTable = true;
                DatDichVuApi.search(searchParamsDatDichVu).then(res => {
                    this.dsDatDichVu = res as any;
                    this.searchParamsDatDichVu.totalItems = (res as any).length;
                    //this.searchParamsDatDichVu.page = (res.pagination.page as any) + 1;
                    this.loadingTable = false;
                });
            },
            showGhiNhan(datDichVu: DatDichVu) {
                this.dialog = true;
                this.datDichVu = datDichVu;
            },
            ghiNhan() {
                DatDichVuApi.update(this.datDichVu.datDichVuID, this.datDichVu).then(res => {
                    this.dialog = false;
                    this.getDataFromApi(this.searchParamsDatDichVu);
                    this.$snotify.success('Ghi nhận thành công!');
                }).catch(res => {
                    this.$snotify.error('Ghi nhận thất bại!');
                });
            },

            exportFile() {
                return APIS.HOST + 'Export/ExportKhachDatDichVu?ten=' + this.searchParamsDatDichVu.tenDichVu +
                    '&tuNgay=' + this.searchParamsDatDichVu.tuNgay +
                    '&denNgay=' + this.searchParamsDatDichVu.denNgay +
                    '&toaNhaID=' + this.searchParamsDatDichVu.toaNhaID;
            },
            confirmDelete(datDichVu: DatDichVu): void {
                this.selectedDatDichVu = datDichVu;
                this.dialogConfirmDelete = true;
            },
            deleteDatDichVu(): void {
                DatDichVuApi.delete(this.selectedDatDichVu.datDichVuID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsDatDichVu);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>
<style>
    .xxx .theme--light.v-pagination .v-pagination__item {
        display: -webkit-box !important;
        background: #fff;
        color: #000;
        width: auto;
        min-width: 34px;
        padding: 0 5px;
    }
</style>
