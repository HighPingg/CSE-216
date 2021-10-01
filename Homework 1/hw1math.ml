type tree =
    | Const of int
    | Var of string
    | Plus of node
    | Minus of node
    | Mult of node
    | Div of node

    and 
        node = {arg1: tree;
                arg2: tree};;

let rec evaluate expression = match expression with

    | Const const -> const

    | Var var -> 0

    | Plus {arg1 = arg1; arg2 = arg2} -> evaluate arg1 + evaluate arg2
    
    | Minus {arg1 = arg1; arg2 = arg2} -> evaluate arg1 - evaluate arg2
    
    | Mult {arg1 = arg1; arg2 = arg2} -> evaluate arg1 * evaluate arg2
    
    | Div {arg1 = arg1; arg2 = arg2} -> evaluate arg1 / evaluate arg2;;