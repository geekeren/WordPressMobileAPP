
var date = require('date-utils')

class TimeUtil {
    getTimeString(date): String {


        // return "";
        var currentDate = new Date();
        var thisDate = this.convertDateFromString(date);
        //var duration = currentDate.getTime() - thisDate.getTime();
        // var day = Date.compare(currentDate, thisDate);
        var duration = currentDate.getSecondsBetween(thisDate);
        var isBefore = thisDate.isBefore(currentDate);
        var str = "";
        var duration_abs = Math.abs(duration);
        if (duration >= 0 && duration <= 60) {
            return "马上";
        } else if (duration < 0 && duration > -60) {
            return "刚刚";
        } else if (duration_abs >= 60 && duration_abs < 3600) {

            str = parseInt(duration_abs / 60 )+ "分钟";
        } else if (duration_abs >= 3600 && duration_abs < 86400) {

            str = parseInt(duration_abs / 3600 )+ "小时";
        } else if (duration_abs >= 86400 && duration_abs < 2592000) {

            str = parseInt(duration_abs / 86400) + "天";
        }
        str = str + (isBefore ? "前" : "后");

        if (duration_abs >= 2592000){
        str = thisDate.toFormat("YYYY-MM-DD");
        }

        return str;
    }
    convertDateFromString(dateString): Date {
        if (dateString) {
            var arr1 = dateString.split("T");
            var sdate = arr1[0].split('-');
            var times = arr1[1].split(':');
            var date = new Date(sdate[0], sdate[1] - 1, sdate[2], times[0], times[1], times[2]);
            return date;
        }
    }
}
module.exports = TimeUtil;