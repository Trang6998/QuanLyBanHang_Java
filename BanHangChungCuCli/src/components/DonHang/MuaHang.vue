<template>
    <v-dialog v-model="dialog" max-width="700" persistent>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>Ghi chú khách hàng</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text pa-0>
                    <v-layout row wrap>
                        <v-flex xs12>
                            <label v-if="donHang.laTre"><b>Hàng được trả trễ hơn so với dự kiến:</b></label>
                        </v-flex>
                        <v-flex xs12 v-if="donHang.laTre">
                            <v-textarea rows="3" v-model="donHang.lyDoTraTre"
                                        placeholder="Lý do" readOnly
                                        label="Lý do trả trễ"
                                        :error-messages="errors.collect('Lý do trả trễ', 'frmAddEdit')"
                                        v-validate="''"
                                        data-vv-scope="frmAddEdit"
                                        data-vv-name="Lý do trả trễ"
                                        hide-details>
                            </v-textarea>
                        </v-flex>
                        <v-flex xs12 v-if="donHang.laTre">
                            <v-layout row wrap>
                                <v-flex xs12>
                                    Thời gian trả dự kiến:
                                </v-flex>
                                <v-flex xs12>
                                    <v-layout nowrap>
                                        <v-flex xs6>
                                            <v-datetimepicker readOnly label="Từ ngày" hide-details v-model="donHang.henTraTu"></v-datetimepicker>
                                        </v-flex>
                                        <v-flex xs6>
                                            <v-datetimepicker readOnly label="Đến ngày" hide-details v-model="donHang.henTraDen"></v-datetimepicker>
                                        </v-flex>
                                    </v-layout>
                                </v-flex>

                            </v-layout>

                        </v-flex>
                        <v-flex xs12 v-if="donHang.tinhTrang == 3 || donHang.tinhTrang == 4">
                            <div>Thời gian trả thực tế:{{donHang.ngayGioTraThucTe | moment('DD/MM/YYYY hh:mm')}}</div>
                        </v-flex>
                        <v-flex xs12>
                            <div>Trạng thái đơn: {{donHang.trangThaiDon}}</div>
                        </v-flex>
                    </v-layout>
                </v-card-text>
            </v-card>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';
    import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    import { DonDatHang } from '@/models/DonDatHang';
    import DonDatHangApi from '@/apiResources/DonDatHangApi';
    import APIS from '@/apisServer';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                isUpdate: false,
                loading: false,
                dialog: false,
                donDatHang: {} as DonDatHang,
                dsChiTietDonDatHang: [] as ChiTietDonDatHang[],
                tableHeader: [
                    { text: 'Sản phẩm', value: '#', align: 'left', sortable: false },
                    { text: 'Đơn giá', value: 'donGia', align: 'left', sortable: false },
                    { text: 'Số lượng', value: 'soLuong', align: 'left', sortable: false },
                    { text: 'Số tiền', value: 'soTien', align: 'left', sortable: false },
                    { text: 'Thao tác', value: '#', align: 'left', sortable: false },
                ],
                searchParamsChiTietDonDatHang: { includeEntities: true, rowsPerPage: 0 } as ChiTietDonDatHangApiSearchParams,
                loadingTable: false,
                selectedChiTietDonDatHang: {} as ChiTietDonDatHang,
                dialogConfirmDelete: false,
                APIS: APIS,
                daNhanDon: false,
                donHang: {} as DonDatHang,
                laTre: false,
                lyDo: '',
                trangThaiDon: -1
            }
        },
        watch: {
        },
        mounted() {
        },
        methods: {
            show(id: number) {
                this.dialog = true;
                this.searchParamsChiTietDonDatHang.donDatHangID = id;
                this.getDataFromApi(this.searchParamsChiTietDonDatHang);
                DonDatHangApi.detail(id).then(res => {
                    this.donHang = res;
                    this.trangThaiDon = res.tinhTrang;
                    if (res.tinhTrang === 1 || res.tinhTrang === 0)
                        this.daNhanDon = false;
                    else
                        this.daNhanDon = true;
                })
            },
            hide() {
                this.dialog = false;
            },
            muaNgay() {
                this.donHang.tinhTrang = 1 // mua hàng
                this.donHang.ngayDat = this.$moment();
                DonDatHangApi.update(this.donHang.donDatHangID, this.donHang).then(res => {
                    this.$emit("getData");
                    this.daNhanDon = true;
                    this.$snotify.success('Mua hàng thành công');
                }).catch(res => {
                    this.$snotify.error('Mua hàng thất bại!');
                });
            },
            getDataFromApi(searchParamsChiTietDonDatHang: ChiTietDonDatHangApiSearchParams): void {
                this.loadingTable = true;
                ChiTietDonDatHangApi.search(searchParamsChiTietDonDatHang).then(res => {
                    this.dsChiTietDonDatHang = res.data;
                    this.searchParamsChiTietDonDatHang.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
            },
            confirmDelete(chiTietDonDatHang: ChiTietDonDatHang): void {
                this.selectedChiTietDonDatHang = chiTietDonDatHang;
                this.dialogConfirmDelete = true;
            },
            deleteChiTietDonDatHang(): void {
                ChiTietDonDatHangApi.delete(this.selectedChiTietDonDatHang.chiTietDonDatHangID).then(res => {
                    this.$snotify.success('Xóa thành công!');
                    this.getDataFromApi(this.searchParamsChiTietDonDatHang);
                    this.dialogConfirmDelete = false;
                }).catch(res => {
                    this.$snotify.error('Xóa thất bại!');
                });
            },
        }
    });
</script>
<style>
</style>

