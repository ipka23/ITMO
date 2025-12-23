class Program
{
    readonly static string info = """
                                  Commands:
                                  expr <expression>         Set expression
                                  set <variable> <value>    Set variable
                                  do                        Evaluate
                                  info                      Commands info
                                  exit                      Quit
                                  """;

    static Dictionary<string, string> variables;
    static string expr;
    static string[] tokens;

    static void Main(string[] args)
    {
        // Console.WriteLine(info);
        // string[] tokens;
        variables = new Dictionary<string, string>();
        while (true)
        {
            Console.Write("> ");
            string input = Console.ReadLine();

            if (input.StartsWith("expr "))
            {
                string[] line = input.Split(" ", 2);
                expr = line[1];
                tokens = new string[expr.Length];
                for (var i = 0; i < expr.Length; i++)
                {
                    tokens[i] = expr[i].ToString();
                }
                // for (var i = 5; i - 5 < input.Length; i++)
                // {
                //     Console.WriteLine(input[i].ToString());
                //     tokens.Append(input[i].ToString());
                // }
                // expr = String.Join("", tokens);
            }
            else if (input.StartsWith("set ") && !String.IsNullOrEmpty(expr))
            {
                char[] varValChars = new char[] { };
                for (var i = 4; i < input.Length; i++)
                {
                    varValChars[i] = input[i];
                }

                string varValString = new String(varValChars);
                String[] varVal = varValString.Split(" ", 2);

                string variable = varVal[0];
                string value = varVal[1];
                variables[variable] = value;
            }
            else if (input.Equals("do") && !String.IsNullOrEmpty(expr))
            {
                Console.WriteLine($"before: {expr}");
                string result = ShuntingYard(tokens);
                Console.WriteLine($"after: {result}");
            }
            else if (input.Equals("exit"))
            {
                break;
            }
            else if (input.Equals("info"))
            {
                Console.WriteLine(info);
            }
            else
            {
                Console.WriteLine("Enter correct data!");
            }
        }
    }


    static string ShuntingYard(string[] tokens)
    {
        Dictionary<string, int> operators = new Dictionary<string, int>
        {
            { "+", 1 },
            { "-", 1 },
            { "*", 2 },
            { "/", 2 }
        };

        Stack<string> stack = new Stack<string>();
        List<string> rpn = new List<string>();

        foreach (var token in tokens)
        {
            List<string> opps = new List<string> { "+", "-", "*", "/" };
            // bool isVar = !long.TryParse(token, out long result) && !opps.Contains(token) && !token.Equals("(") && !token.Equals(")"); 
            bool isVarOrConst = !opps.Contains(token) && !token.Equals("(") && !token.Equals(")");
            if (isVarOrConst)
            {
                rpn.Add(token);
            }
            else if (opps.Contains(token))
            {
                //expr a+b*(c-d)
                
                //expr 3+2*(6-4)
                if (stack.Count != 0)
                {
                    //expr (a+b)
                    while (opps.Contains(stack.First()) && operators[stack.First()] >= operators[token])
                    {
                        rpn.Add(stack.Pop());
                        if (stack.Count == 0) break;
                    }
                }
               
                stack.Push(token);
            }
            else if (token.Equals("("))
            {
                stack.Push(token);
            }
            else if (token.Equals(")"))
            {
                while (!stack.First().Equals("("))
                    // Console.WriteLine(stack.First());
                {
                    rpn.Add(stack.Pop());
                    // Console.WriteLine(stack.Pop());
                }
                stack.Pop();
            }
        }

        while (stack.Count > 0)
        {
            rpn.Add(stack.Pop());
        }
        // List<string> reversed = rpn.Reverse().ToList();
        return String.Join("", rpn);
    }
}
//expr (a+b)