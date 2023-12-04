package com.ssoft.common.widget

import android.content.Context
import android.graphics.Typeface

class TypeFactory {
    private val A_THIN = "fonts/Prompt-Thin.ttf"
    private val A_LIGHT = "fonts/Prompt-Light.ttf"
    private val A_MEDIUM = "fonts/Prompt-Medium.ttf"
    private val A_BOLD = "fonts/Prompt-Bold.ttf"
    private val A_REGULAR = "fonts/Prompt-Regular.ttf"


    var light: Typeface? = null
    var medium: Typeface? = null
    var thin: Typeface? = null
    var bold: Typeface? = null
    var regular: Typeface? = null




    constructor(context: Context) {
        light = Typeface.createFromAsset(context.assets, A_LIGHT)
        medium = Typeface.createFromAsset(context.assets, A_MEDIUM)
        thin = Typeface.createFromAsset(context.assets, A_THIN)
        bold = Typeface.createFromAsset(context.assets, A_BOLD)
        regular = Typeface.createFromAsset(context.assets, A_REGULAR)

    }

}