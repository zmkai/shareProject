function ajax(options){
    var _options = {
        url:null,
        data:null,
        method:'GET',
        header:null,
        success:null,
        error:null,
        progress:null
    }
    
    var key;
    for(key in options){
        if(_options.hasOwnProperty(key) && !options.hasOwnProperty(key)){
            options[key] = _options[key];
        }
    }

    var fd = new FormData();
    for(key in options.data){
        fd.append(key,options.data[key]);
    }

    var xhr = new XMLHttpRequest();
    console.log(options)
    xhr.upload.addEventListener('progress',options.progress,false);
    xhr.addEventListener('load',options.success,false);
    xhr.addEventListener('error',options.error,false);
    xhr.open(options.method,options.url);
    // xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    xhr.send(fd);
}