package id.co.edtslib.edtsds.otpverification

interface OtpVerificationDelegate {
    fun onExpired()
    fun onSubmit(otp: String?)
}