//package com.infowebmentsolution.ghosh.clickforflick.Response;
//
//import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
//import com.infowebmentsolution.ghosh.clickforflick.Model.NextVideoList;
//
//public class NextVideoResponse {
//    private NextVideoList[] result1;
//    private NextVideoList result2;
//    private String msg;
//
//    public NextVideoResponse(NextVideoList[] result1, NextVideoList result2, String msg) {
//        this.result1 = result1;
//        this.result2 = result2;
//        this.msg = msg;
//    }
//
//    public NextVideoList[] getResult1() {
//        return result1;
//    }
//
//    public void setResult1(NextVideoList[] result1) {
//        this.result1 = result1;
//    }
//
//    public NextVideoList getResult2() {
//        return result2;
//    }
//
//    public void setResult2(NextVideoList result2) {
//        this.result2 = result2;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//}
//

package com.infowebmentsolution.ghosh.clickforflick.Response;

        import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
        import com.infowebmentsolution.ghosh.clickforflick.Model.NextVideoList;

public class NextVideoResponse {
    private HomeMovieList[] result2;
    private NextVideoList[] result1;
    private String msg;

    public NextVideoResponse(HomeMovieList[] result2, NextVideoList[] result1, String msg) {
        this.result2 = result2;
        this.result1 = result1;
        this.msg = msg;
    }

    public HomeMovieList[] getresult2() {
        return result2;
    }

    public void setresult2(HomeMovieList[] result2) {
        this.result2 = result2;
    }

    public NextVideoList[] getresult1() {
        return result1;
    }

    public void setresult1(NextVideoList[] result1) {
        this.result1 = result1;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
