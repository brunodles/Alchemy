# Jsoup Parser (WIP)
A parser to use html pages as API for sites that does not provide api.

We use [css selectors](https://www.w3schools.com/cssref/css_selectors.asp) to collect objects on those pages.
Click on link for some more info.

## WIP ?
This project is currently being develop, suggestions are welcome, just create a issue.

## Next Steps
1. Multiple Transformer
2. Multiple modules project
3. Unified declaration
4. Json mapper
5. Sample Project

## Ideal Flow

This flow is not fully working.
The idea is to let it chain through all layers and also restart the work.
For the future we will only have Transformers.

Layer              | Input   | Output  | Description
-------------------|---------|---------|-------------
Selector           | String  | Element | Get's a css selector string and transform it into a Element
Collector          | Element | String  | Get's a Element to collect it's value from `content` or `attribute`.
Transformer        | String  | Type    | Get's a Element Value and transform it into something else.
Nested Transformer | String  | Element | Get's a Element Value and transform it into an Element or custom type, may restart the process.
