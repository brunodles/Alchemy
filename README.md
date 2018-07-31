# Alchemy (WIP)
A parser to use html pages as API for sites that does not provide api.

We use [css selectors](https://www.w3schools.com/cssref/css_selectors.asp) to collect objects on those pages.
Click on link for some more info.

## WIP ?
This project is currently being develop, suggestions are welcome, just create a issue.

## Next Steps
1. Json mapper
2. Sample Module

## Transmutation Flow
This library works transmuting elements into another.

Usually this library starts with _html elements_ and transmute it into a object.
The idea is to have multiple transmutations until we have the wanted element.

### Sample Flow

Transmutation | What does it do?
--------------|-------------------
Selector      | Find elements on html page, which is a element too.
Collector     | Collects data from the element. Content, attribute and html are possible data from an element.
Transformer   | Transmute into an _Object Type_. It's a complex transmutation that may receive a transformer. This can also be used to restart the process  