package com.codeex.domain.usecases.base

import com.codeex.domain.model.State

interface BaseUseCase<T : Any, R : Any> {
    suspend operator fun invoke(param: T): State<R>
}