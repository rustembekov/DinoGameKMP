class InputController {
    var onJump: (() -> Unit)? = null

    fun handleInput(jumpPressed: Boolean) {
        if (jumpPressed) onJump?.invoke()
    }
}
