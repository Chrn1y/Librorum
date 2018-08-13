package com.dev.librorum.data

data class BookData(var _id: Int,
                    var url: String,
                    var categoryId: String,
                    var picture: String,
                    var author: String,
                    var name: String,
                    var series: String,
                    var ISBN: String,
                    var language: String,
                    var description: String)
