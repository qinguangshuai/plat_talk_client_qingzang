package com.kylindev.totalk.net;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mac-yk on 2017/3/18.
 */

public interface ServerAPI {

    @POST(I.REQUEST.PATH+ I.REQUEST.UPLOAD_UNCAUGHT)
    @Multipart
    Observable<Result<String>> uploadCrash(
            @Part("file\";filename=\"throwable.log\"") RequestBody file,
            @Query(I.UNCAUGHT.PATH) String path, @Query(I.UNCAUGHT.FILE_NAME) String name);




    @POST(I.REQUEST.PATH+ I.REQUEST.UPLOAD_FILE)
    @Multipart
    Observable<Result<String>> uploadImage(@Part("file\";filename=\"throwable.log\"") RequestBody body,
                                                 @Query(I.FILE.FILENAME) String name,@Query(I.LEVEL)int level);



    @POST(I.REQUEST.PATH+ I.REQUEST.CHECK_PIC)
    Observable<Result<String>> checkPic();

}
