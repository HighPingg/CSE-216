type ('a, 'b) tree =
    | Const of 'a
    | Var of 'b
    | Plus of ('a, 'b) node
    | Minus of ('a, 'b) node
    | Mult of ('a, 'b) node
    | Div of ('a, 'b) node

    and 
        ('a, 'b) node = {arg1: ('a, 'b) tree;
                        arg2: ('a, 'b) tree};;

let rec evaluate expression = match expression with

    | Const const -> const

    | Var var -> 0

    | Plus {arg1 = arg1; arg2 = arg2} -> evaluate arg1 + evaluate arg2
    
    | Minus {arg1 = arg1; arg2 = arg2} -> evaluate arg1 - evaluate arg2
    
    | Mult {arg1 = arg1; arg2 = arg2} -> evaluate arg1 * evaluate arg2
    
    | Div {arg1 = arg1; arg2 = arg2} -> evaluate arg1 / evaluate arg2;;