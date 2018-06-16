package global.msnthrp.smarty_smart_cli.utils

class Cryptool(secret: String) {

    private val secretBytes = sha256Raw(secret.toByteArray())

    fun encrypt(seed: String): String {
        val iv = randomString().toByteArray().copyOfRange(0, 16)
        val enc = Aes256.encrypt(iv, secretBytes, seed.toByteArray())
        return toBase64(iv + enc)
    }

}