package com.example.common.parser

import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

object SpringElParser {
    fun getDynamicValue(
        parameterName: Array<String>,
        args: Array<Any>,
        key: String,
    ): Any? {
        val parser = SpelExpressionParser()
        val context = StandardEvaluationContext()

        for (i in parameterName.indices) {
            context.setVariable(parameterName[i], args[i])
        }

        return parser.parseExpression(key).getValue(context, Any::class)
    }
}
