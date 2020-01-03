<template>
    <v-dialog v-model="dialog" max-width="700" persistent>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="white--text" style="height: 40px" v-bind:style="{ background: activeColor} ">
                    <h3>Chi tiết đơn </h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text pa-0>
                    <v-layout row wrap>
                        <v-flex xs12>
                            <v-data-table :headers="tableHeader"
                                          :items="dsChiTietDonDatHang"
                                          
                                          :loading="loadingTable" hide-actions
                                          class="elevation-1" style="border-collapse: unset; background-color: unset!important">
                                <template slot="items" slot-scope="props">
                                    <td style="width: 40% ;padding-left: 0px!important; white-space: nowrap" class="text-nowrap">
                                        <v-layout nowrap style="min-width: 200px">
                                            <v-flex sm4>
                                                <v-img v-if="props.item.anhSanPham != null" slot="activator"
                                                       :src="APIS.HOST + 'fileupload/download?key=' + props.item.anhSanPham"
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
                                                <v-img v-else src='/static/images/photo-3x4.jpg' slot="activator"
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
                                            <v-flex sm8>
                                                <div>{{ props.item.tenSanPham }}</div>
                                            </v-flex>
                                        </v-layout>
                                    </td>
                                    <td class="text-xs-center">{{ props.item.giaXuat  | currency('', 0,{ thousandsSeparator: '.' })}}</td>
                                    <td class="text-xs-center">{{ props.item.soLuong }}</td>
                                    <td class="text-xs-center">{{ props.item.thanhTien  | currency('', 0,{ thousandsSeparator: '.' })}}</td>
                                    <td class="text-xs-center" style="width:80px;">
                                        <v-btn :disabled="donHang.tinhTrang != 0" flat color="red" small class="ma-0" @click="confirmDelete(props.item)">
                                            Xóa
                                        </v-btn>
                                    </td>
                                </template>
                                <template slot="no-data">
                                    <p class="text-xs-center mb-0">Không có dữ liệu</p>
                                </template>
                            </v-data-table>
                        </v-flex>
                        <v-flex xs12>
                            <v-layout nowrap style="font-size: 16px;">
                                <v-spacer></v-spacer>
                                <b>Tổng tiền: </b>&nbsp;<span style="color: chocolate">{{donHang.tongTien  | currency('', 0,{ thousandsSeparator: '.' })}}</span>&nbsp;
                            </v-layout>
                        </v-flex>
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
                                            <v-datetimepicker readonly label="Từ ngày" hide-details v-model="donHang.henTraTu"></v-datetimepicker>
                                        </v-flex>
                                        <v-flex xs6>
                                            <v-datetimepicker readonly label="Đến ngày" hide-details v-model="donHang.henTraDen"></v-datetimepicker>
                                        </v-flex>
                                    </v-layout>
                                </v-flex>

                            </v-layout>

                        </v-flex>
                        <v-flex xs12>
                            <div>Trạng thái đơn: {{donHang.trangThaiDon}}</div>
                        </v-flex>

                        <v-flex xs12 v-if="donHang.tinhTrang != 0">
                            <div>Ngày đặt: {{donHang.ngayDat | moment('DD/MM/YYYY hh:mm')}}</div>
                        </v-flex>
                        <v-flex xs12 v-if="donHang.tinhTrang == 3 || donHang.tinhTrang == 4">
                            <div>Thời gian trả thực tế: {{donHang.ngayGioTraThucTe | moment('DD/MM/YYYY hh:mm')}}</div>
                        </v-flex>
                    </v-layout>
                    <v-layout row wrap v-if="donHang.tinhTrang != 0">
                        <v-flex xs12>
                            <v-layout row wrap>
                                <v-flex xs12>
                                    Thời gian hẹn lấy:
                                    <span v-if="donHang.henLayTu != null && donHang.henLayDen != null">
                                        <span v-if="soSanhNgay(donHang.henLayTu, donHang.henLayDen)">
                                            {{ donHang.henLayTu === null ? "" : donHang.henLayTu|moment('HH:mm') }} -
                                            {{ donHang.henLayDen === null ? "" : donHang.henLayDen|moment('HH:mm') }}
                                            {{ donHang.henLayTu === null ? "" : donHang.henLayTu|moment('DD/MM/YY') }}
                                        </span>
                                        <span v-else>
                                            {{donHang.henLayTu |moment("hh:mm DD/MM/YYYY")}} -
                                            {{donHang.henLayDen |moment("hh:mm DD/MM/YYYY")}}
                                        </span>

                                    </span>
                                    <span v-else-if="donHang.henLayTu != null && donHang.henLayDen == null">
                                        Sau {{donHang.henLayTu |moment("hh:mm DD/MM/YYYY")}}
                                    </span>
                                    <span v-else-if="donHang.henLayTu == null && donHang.henLayDen != null">
                                        Trước {{donHang.henLayDen |moment("hh:mm DD/MM/YYYY")}}
                                    </span>
                                    <span v-else>
                                        Mọi thời gian
                                    </span>
                                </v-flex>

                            </v-layout>
                        </v-flex>
                        <v-flex xs12>
                            <div>Ghi chú: {{donHang.ghiChu? donHang.ghiChu : 'Không'}}</div>
                        </v-flex>
                    </v-layout>
                    <v-layout row wrap v-if="donHang.tinhTrang == 0">
                        <v-flex xs12>
                            <v-layout row wrap>
                                <v-flex xs12>
                                    Hẹn thời gian lấy hàng:
                                </v-flex>
                                <v-flex xs12>
                                    <v-layout row wrap>
                                        <v-flex xs12 md6>
                                            <v-datetimepicker v-model="donHang.henLayTu" label="Từ ngày" :min="new Date()"
                                                              clearable></v-datetimepicker>
                                        </v-flex>
                                        <v-flex xs12 md6>
                                            <v-datetimepicker v-model="donHang.henLayDen" label="Đến ngày" :min="thoiGianHenToiThieu"
                                                              clearable></v-datetimepicker>
                                        </v-flex>
                                    </v-layout>
                                </v-flex>
                            </v-layout>
                        </v-flex>
                        <v-flex xs12>
                            <v-textarea rows="3" v-model="donHang.ghiChu"
                                        label="Ghi chú"
                                        :error-messages="errors.collect('Ghi chú', 'frmAddEdit')"
                                        v-validate="''"
                                        data-vv-scope="frmAddEdit"
                                        data-vv-name="Ghi chú"
                                        hide-details>
                            </v-textarea>
                        </v-flex>
                        <v-flex xs12>
                            <v-layout nowrap>
                                <v-spacer></v-spacer>
                                <v-btn flat small color="primary" :disabled="loading" :loading="loading" @click="muaNgay">Mua ngay</v-btn>
                            </v-layout>
                        </v-flex>
                    </v-layout>
                </v-card-text>
            </v-card>
            <v-dialog v-model="dialogConfirmDelete" max-width="290">
                <v-card>
                    <v-card-title class="headline">Xác nhận xóa</v-card-title>
                    <v-card-text class="pt-3">Bạn có chắc chắn muốn xóa?</v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn @click.native="dialogConfirmDelete=false" flat>Hủy</v-btn>
                        <v-btn color="red darken-1" @click.native="deleteChiTietDonDatHang" flat>Xác Nhận</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
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
                activeColor: '#e46c0a',
                isUpdate: false,
                loading: false,
                dialog: false,
                donDatHang: {} as DonDatHang,
                dsChiTietDonDatHang: [] as ChiTietDonDatHang[],
                tableHeader: [
                    { text: 'Sản phẩm', value: '#', align: 'left', sortable: false },
                    { text: 'Đơn giá', value: 'donGia', align: 'center', sortable: false },
                    { text: 'Số lượng', value: 'soLuong', align: 'center', sortable: false },
                    { text: 'Số tiền', value: 'soTien', align: 'center', sortable: false },
                    { text: 'Thao tác', value: '#', align: 'center', sortable: false },
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
                trangThaiDon: -1,
                thoiGianHenToiThieu: null as any
            }
        },
        watch: {
            'donHang.henLayTu': function (val) {
                this.thoiGianHenToiThieu = this.$moment(val).add(15, 'm');
            },
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
                    if (res.tinhTrang === 1 || res.tinhTrang === 0) {
                        this.daNhanDon = false;
                        this.activeColor = '#e46c0a';
                    }
                    else {
                        this.daNhanDon = true;
                        if (res.tinhTrang == 2)
                            this.activeColor = '#4CAF50';
                        if (res.tinhTrang == 3)
                            this.activeColor = '#FF5252';
                        if (res.tinhTrang == 4)
                            this.activeColor = '#2196F3';
                    }
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
                    this.dialog = false;
                    this.$snotify.success('Mua hàng thành công');
                }).catch(res => {
                    this.$snotify.error('Mua hàng thất bại!');
                });
            },
            soSanhNgay(tuNgay: Date, denNgay: Date) {
                if (tuNgay.toString().slice(0, 10) == denNgay.toString().slice(0, 10)) {
                    return true;
                }
                else return false;
            },
            getDataFromApi(searchParamsChiTietDonDatHang: ChiTietDonDatHangApiSearchParams): void {
                this.loadingTable = true;
                ChiTietDonDatHangApi.search(searchParamsChiTietDonDatHang).then(res => {
                    this.dsChiTietDonDatHang = res as any;
                    //this.searchParamsChiTietDonDatHang.totalItems = res.pagination.totalItems;
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

