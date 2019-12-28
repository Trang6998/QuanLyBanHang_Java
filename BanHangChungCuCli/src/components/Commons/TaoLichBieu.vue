<template>
    <v-card class="ds-calendar-event-popover-card"
            :class="classes">
        <v-toolbar extended flat
                   :style="styleHeader">
            <v-toolbar-title slot="extension">
                <v-text-field single-line hide-details solo flat autofocus
                              label="Điền tiêu đề"
                              v-model="details.TieuDe"></v-text-field>
            </v-toolbar-title>
            <!--<v-btn v-if="!details.readonly"
                   color="secondary"
                   small absolute bottom left fab icon
                   @click="edit">
                <v-icon>{{ icons.edit }}</v-icon>
            </v-btn>-->
            <slot name="eventCreatePopoverToolbarLeft" v-bind="slotData"></slot>
            <v-spacer></v-spacer>
            <slot name="eventCreatePopoverToolbarRight" v-bind="slotData"></slot>
            <slot name="eventCreatePopoverToolbarSave" v-bind="slotData">
                <v-btn class="ds-create-popover-save"
                       flat
                       :disabled="!isValid"
                       :style="styleText"
                       @click="save">
                    <v-icon left>{{ icons.save }}</v-icon>
                    <span>{{ labels.save }}</span>
                </v-btn>
            </slot>
            <slot name="eventCreatePopoverToolbarClose" v-bind="slotData">
                <v-btn icon
                       @click="close"
                       :style="styleText">
                    <v-icon>{{ icons.close }}</v-icon>
                </v-btn>
            </slot>
        </v-toolbar>
        <v-card-text class="pa-0">
            <slot name="eventCreatePopoverBodyTop" v-bind="slotData"></slot>
            <v-list>
                <v-list-tile>
                    <v-list-tile-avatar>
                        <v-icon>access_time</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <small>Thời gian diễn ra:</small>
                        <v-text-field type="datetime-local" single-line hide-details solo flat full-width
                                      label="Thời gian diễn ra"
                                      required
                                      v-model="details.ThoiGianDienRa">

                        </v-text-field>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile>
                    <v-list-tile-avatar>
                        <v-icon>access_time</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <v-layout row wrap>
                            <v-flex xs12 md6 pr-2>
                                <small>Thời lượng:</small>
                                <v-text-field type="number" single-line hide-details solo flat full-width
                                              label="Nhập thời lượng"
                                              required
                                              v-model="details.ThoiLuongDienRa">
                                </v-text-field>
                            </v-flex>

                            <v-flex xs12 md6>
                                <small>Đơn vị thời lượng:</small>
                                <v-select :items="dsDonViThoiLuong"
                                          required
                                          single-line hide-details solo flat full-width
                                          label="Chọn đơn vị thời lượng"
                                          v-model="details.DonViThoiLuong"></v-select>
                            </v-flex>
                        </v-layout>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile>
                    <v-list-tile-avatar>
                        <v-icon>location_on</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <small>Địa điểm:</small>
                        <v-text-field single-line hide-details solo flat full-width
                                      label="Nhập địa điểm diễn ra"
                                      v-model="details.DiaDiem"></v-text-field>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile>
                    <v-list-tile-avatar>
                        <v-icon>group</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <small>Thành phần tham gia:</small>
                        <v-textarea hide-details single-line solo flat full-width
                                    label="Nhập thành phần tham gia"
                                    v-model="details.ThanhPhan"></v-textarea>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile>
                    <v-list-tile-avatar>
                        <v-icon>gavel</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <small>Chủ trì:</small>
                        <v-text-field single-line hide-details solo flat full-width
                                      lable="Nhập người chủ trì"
                                      v-model="details.ChuTri"></v-text-field>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile>
                    <v-list-tile-avatar>
                        <v-icon>list</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <small>Nội dung:</small>
                        <v-textarea hide-details single-line solo flat full-width
                                    label="Nhập nội dung"
                                    v-model="details.NoiDung"></v-textarea>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile>
                    <v-list-tile-avatar>
                        <v-icon>invert_colors</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <small>Màu nền:</small>
                        <v-select single-line hide-details solo flat full-width
                                  :items="$dayspan.colors"
                                  label="Chọn màu nền"
                                  v-model="details.MauNen">
                            <template slot="item" slot-scope="{ item }">
                                <v-list-tile-content>
                                    <div class="ds-color-option" :style="{backgroundColor: item.value}" v-text="item.text"></div>
                                </v-list-tile-content>
                            </template>
                        </v-select>
                    </v-list-tile-content>
                </v-list-tile>

                <v-list-tile>
                    <v-list-tile-avatar>
                        <v-icon>remove_red_eye</v-icon>
                    </v-list-tile-avatar>
                    <v-list-tile-content>
                        <v-checkbox label="Hiển thị"
                                    v-model="details.HienThi"></v-checkbox>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
            <slot name="eventCreatePopoverBodyBottom" v-bind="slotData"></slot>
        </v-card-text>
        <slot name="eventCreatePopoverActions" v-bind="slotData"></slot>
    </v-card>
</template>
<script>
    import { CalendarEvent, Calendar, Pattern, Functions as fn } from 'dayspan';


    export default {

        name: 'dsCalendarEventCreatePopover',

        props:
        {
            calendarEvent:
            {
                required: true,
                type: CalendarEvent
            },

            calendar:
            {
                required: true,
                type: Calendar
            },

            close:
            {
                type: Function
            },

            formats:
            {
                validate(x) {
                    return this.$dsValidate(x, 'formats');
                },
                default() {
                    return this.$dsDefaults().formats;
                }
            },

            icons:
            {
                validate(x) {
                    return this.$dsValidate(x, 'icons');
                },
                default() {
                    return this.$dsDefaults().icons;
                }
            },

            labels:
            {
                validate(x) {
                    return this.$dsValidate(x, 'labels');
                },
                default() {
                    return this.$dsDefaults().labels;
                }
            },

            prompts:
            {
                validate(x) {
                    return this.$dsValidate(x, 'prompts');
                },
                default() {
                    return this.$dsDefaults().prompts;
                }
            },

            busyOptions:
            {
                type: Array,
                default() {
                    return this.$dsDefaults().busyOptions;
                }
            }
        },

        computed:
        {
            slotData() {
                return {
                    calendarEvent: this.calendarEvent,
                    calendar: this.calendar,
                    close: this.close,
                    detailsl: this.details
                };
            },

            classes() {
                return {
                    'ds-event-cancelled': this.calendarEvent.cancelled
                };
            },

            styleHeader() {
                return {
                    backgroundColor: this.details.MauNen,
                    color: this.details.MauChu
                };
            },

            styleText() {
                return {
                    color: this.details.MauChu
                };
            },

            startDate() {
                return this.calendarEvent.start.format(this.formats.start);
            },

            busyness() {
                return this.details.HienThi ? this.labels.busy : this.labels.free;
            },

            isValid() {
                return this.$dayspan.isValidEvent(
                    this.details,
                    this.calendarEvent.schedule,
                    this.calendarEvent
                );
            },

            occurs() {
                return this.$dayspan.getEventOccurrence(
                    this.calendarEvent.schedule,
                    this.calendarEvent.start,
                    this.labels,
                    this.formats
                );
            }
        },
        watch: {
            calendarEvent: {
                handler: function (after, before) {
                    this.details.ThoiLuongDienRa =
                        this.$moment(after.end.format('YYYY-MM-DDTHH:mm:ss'))
                            .diff(this.$moment(after.start.format('YYYY-MM-DDTHH:mm:ss')), 'minutes');
                },
                deep: true,
            }
        },
        data: vm => ({
            details: vm.buildDetails(),
            dsDonViThoiLuong: [{
                text: 'Phút',
                value: 'minutes'
            }, {
                text: 'Giờ',
                value: 'hours'
            }, {
                text: 'Ngày',
                value: 'days'
            }]
        }),

        methods:
        {
            edit() {
                var ev = this.getEvent('create-edit');

                this.$emit('create-edit', ev);

                this.finishEvent(ev);
            },

            save() {
                let ev = this.getEvent('creating');

                this.$emit('creating', ev);
                if (!ev.handled && ev.details && ev.calendarEvent) {
                    ev.created = ev.calendarEvent.event;

                    this.$dayspan.setEventDetails(
                        ev.details,
                        ev.created.data,
                        ev.created,
                        ev.calendarEvent
                    );

                    if (ev.calendar) {
                        ev.calendar.addEvent(ev.created);
                        ev.added = true;
                    }

                    this.$emit('created', ev);

                    if (ev.calendar && ev.refresh) {
                        ev.calendar.refreshEvents();
                    }

                    ev.handled = true;

                    this.$emit('event-create', ev.created);
                }

                this.finishEvent(ev);
            },

            finishEvent(ev) {
                if (ev.close) {
                    this.close();

                    this.$emit('create-popover-closed', ev);
                }
            },

            buildDetails() {
                let details = this.$dayspan.copyEventDetails(this.calendarEvent.event.data);

                details.TieuDe = '';
                details.ThoiGianDienRa = this.calendarEvent.start.format('YYYY-MM-DDTHH:mm:ss');
                details.ThoiLuongDienRa = this.$moment(this.calendarEvent.end.format('YYYY-MM-DDTHH:mm:ss'))
                    .diff(this.$moment(this.calendarEvent.start.format('YYYY-MM-DDTHH:mm:ss')));
                return details;
            },

            getEvent(type, extra = {}) {
                return fn.extend({
                    type: type,
                    calendarEvent: this.calendarEvent,
                    calendar: this.calendar,
                    close: this.close,
                    details: this.details,
                    handled: false,
                    added: false,
                    refresh: true,
                    $vm: this,
                    $element: this.$el
                }, extra);
            }
        }
    }
</script>
<style scoped lang="scss">


    .v-btn--floating.v-btn--left {
        margin-left: 0px !important;
        .v-icon

    {
        height: auto;
    }

    }

    .ds-calendar-event-popover-card {
        /deep/ .v-toolbar__extension

    {
        padding: 0 16px !important;
        height: 60px !important;
        align-items: start;
        .v-toolbar__title

    {
        width: 100%;
        margin-left: 56px;
        margin-right: 0px;
        /deep/ .v-input__slot

    {
        background-color: rgba(255,255,255,0.2) !important;
        input

    {
        caret-color: rgba(0,0,0,.87) !important;
    }

    }
    }
    }
    }

    .v-text-field--full-width {
        width: 100%;
        padding: 0 !important;
        .v-input__control

    {
        padding: 3px 0 !important;
    }

    }

    .v-card__text {
        padding: 16px 0;
        .v-list

    {
        padding-bottom: 0px;
        > div:first-child

    {
    }

    /deep/ .v-list__tile {
        height: auto !important;
    }

    }
    }

    .ds-create-popover-save {
        background-color: transparent !important;
    }

    .ds-color-option {
        width: 100%;
        color: white;
        padding: 4px;
    }
</style>
