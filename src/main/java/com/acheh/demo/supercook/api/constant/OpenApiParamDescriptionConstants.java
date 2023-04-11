package com.acheh.demo.supercook.api.constant;

public final class OpenApiParamDescriptionConstants {

    private OpenApiParamDescriptionConstants() {
        // util class
    }

    public static final String SEARCH_PARAM_DESCRIPTION = "__API Search parameter operators:__\n\n" +
            " [__:__]  Equality  value check using equality. \n" +
            "                _Example:_ `search=title:Chicken`\n\n" +
            " [__>__]  Greater then  \n" +
            "                _Example:_ `search=servings>5`\n\n" +
            " [__<__]  Less than  \n" +
            "                _Example:_ `search=servings<10`\n\n" +
            " [__~__]  Like  \n" +
            "                _Example:_ `search=title*Chic`\n\n" +
            " [__AND/OR__] construction  \n" +
            "                _Example:_ `search=title:*Chic AND (description*baked OR description*oven)`\n\n";


}
