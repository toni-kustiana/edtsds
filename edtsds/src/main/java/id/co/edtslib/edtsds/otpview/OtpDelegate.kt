package id.co.edtslib.edtsds.otpview

interface OtpDelegate {
    fun onCompleted(pin: String)
    fun onTextChanged(text: String)
}