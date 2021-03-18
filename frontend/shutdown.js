var exec = require('child_process').exec;

function shutdown(callback){
    exec('sudo shutdown now', function(error, stdout, stderr){ callback(stdout); });
}

module.exports.shutdown = shutdown;