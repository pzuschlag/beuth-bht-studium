/**
 * Created by Charlie on 08.01.17.
 */

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

// Schema for the Object in the database.
var VideoSchema = new Schema({
    title : {type: String, required: true},
    description: {type: String, default: ''},
    src: {type: String, required: true},
    length: {type: Number, min:0, required: true},
    playcount: {type: Number, min: 0, default: 0},
    ranking: {type: Number, min: 0, default: 0}
},{
    timestamps: {createdAt: 'timestamp'}
});


// Exporting Schema as model in order to use it like a object
module.exports = mongoose.model('Video', VideoSchema);