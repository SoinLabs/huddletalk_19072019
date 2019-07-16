
var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: process.env.EMAIL,
        pass: process.env.PASSWORD
    }
});

exports.handler = (event, context, callback) => {
    const done = (err, res) => callback(null, {
        statusCode: err ? '400' : '200',
        body: err ? err.message : JSON.stringify(res),
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'POST'
        },
    });

    let _body = JSON.parse(event.body);

    let mailOptions = {
        from: process.env.FROM_EMAIL,
        to: _body.to_email,
        subject: _body.subject,
        html: _body.message
    };

    transporter.sendMail(mailOptions, done(null,_body));
};