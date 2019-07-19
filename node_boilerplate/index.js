exports.handler = (event, context, callback) => {
  const done = (err, res) =>
    callback(null, {
      statusCode: err ? "400" : "200",
      body: err ? err.message : JSON.stringify(res),
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "POST"
      }
    });

  let _body = JSON.parse(event.body);

  transporter.sendMail(mailOptions, done(null, _body));
};
