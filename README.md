# SudoSL

An experiment in JScheme (LISP) without so many damn parentheses.

## Syntax

### Literals

#### Null

```
null
```

#### Booleans

##### True

```
#t
#T
true
```

##### False

```
#f
#F
false
```

#### Integer

```
42
```

#### Double

```
42.0
```

#### Character

```
#\space   ; literal space
#\newline ; literal newline
#\a       ; the 'a' character
```

#### String

```
"string"
symbol
```

### Comments

When the interpreter encounters a `;` it will skip to the end of the line.
```
; this is a comment
local x 10 ; comment at the end of a line
```

### Variables

Variables must be declared with `local` before using them.

```
local x 10
print x ; >> 10
```

Use `=` to set a variable.

```
local x 10
= x 42
print x ; >> 42
```

### Blocks

Expressions evaluated inside of blocks use a new environment with access to the parent environment.

```
local x 10
block
  print x ; >> 10
```

Variables can be re-declared inside the local scope of a block, but do not leak outside the scope.

```
local x 10
block
  local x 42
  print x ; >> 42
print x ; >> 10
```

Blocks that use the keyword `begin` behave the same as the `block` keyword, except the `begin` blocks do not introduce a new environment. Any variables declared within a `begin` block will leak outside the block and any variables re-declared within a `begin` block will produce an error.

```
local x 10
begin
  local y 10
  += y x
print y ; >> 20
```

### Branching

The `then` and `else` blocks in an `if` statement are equivalent to using the `block` keyword and are completely interchangeable. The keywords `then` and `else` are used as syntactic sugar to improve readability.

```
if (<predicate>)
  then ; could use 'block' here instead
    <expression>
    <expression>
  else ; could use 'block' here instead
    <expression>
    <expression>
```

If you don't need a blocked scope (new environment), consider using the `begin` keyword instead of `then`, `else`, or `block`.

```
if (<predicate>)
  begin ; is evaluated if <predicate> is true
    <expression>
    <expression>
  begin ; is evaluated if <predicate> is false
    <expression>
    <expression>
```

If you don't need blocked scope and multi-line expressions, then `if` statements can be written without any block keywords like this:

```
if (<predicate>) (<then>) (<else>)
```

...or this:

```
if (<predicate>)
  <then>
  <else>
```

In `switch` expressions, the `case` blocks evaluate as blocks but don't introduce a new environment. That means if `x` is already declared in a parent scope, and you try to declare a new `local x`, it will error and tell you that `x` is already declared.

```
switch
  case (<predicate>)
    <expression>
    <expression>
  case (<predicate>)
    <expression>
    <expression>
  default
    <expression>
    <expression>
```

`cond` statements behave like `switch` statements, but are written a little differently.

```
cond
  (<predicate>)
    <expression>
    <expression>
  (<predicate>)
    <expression>
    <expression>
  else
    <expression>
    <expression>
```
