#ifndef SRC_MODEL_H_
#define SRC_MODEL_H_

#include <math.h>
#include <string.h>

#include <stack>
#include <vector>

namespace s21 {
const int ALLOCATED_SIZE = 256;

class Model {
private:
    struct Lexeme {
        long double value;
        int priority;
        int type;
    };

    enum Type {
        NUMBER,         // 0
        VARIABLE,       // 1
        OPEN_BRACKET,   // 2
        CLOSE_BRACKET,  // 3
        UNARY_MINUS,    // 4
        BINARY_MINUS,   // 5
        UNARY_PLUS,     // 6
        BINARY_PLUS,    // 7
        MUL,            // 8
        DIV,            // 9
        POW,            // 10
        MOD,            // 11
        COS,            // 12
        SIN,            // 13
        TAN,            // 14
        ACOS,           // 15
        ASIN,           // 16
        ATAN,           // 17
        SQRT,           // 18
        LN,             // 19
        LOG             // 20
    };

    long double result_;

    bool CheckDoubleCorrectness(const char* input_expr_x_, long double& x);

    class InputStringParsing;

    class ReversePolishNotationCalculation;

    bool CheckStrlen(const char* input_expression);
    bool MainCalculation(InputStringParsing& input_string_parsing_obj);

public:
    bool MainFunction(const char* input_expression, long double x);
    bool CheckExpressionCorrectness(const char* input_expression);
    long double GetResult() { return result_; }
};

class Model::InputStringParsing {
public:
    std::vector<Lexeme> lexemes_;

    explicit InputStringParsing(const char* input_expression) {
        return_value_ = true;
        input_cnt_ = lex_amount_ = bracket_count_ = 0;
        lexemes_.reserve(ALLOCATED_SIZE);
        ClearMassiveForLexemes();
        input_expression_ = input_expression;
    }
    bool Parsing();
    void CaseOpenBracket();
    void CaseCloseBracket();
    void CaseMinus();
    void CasePlus();
    void CaseMul();
    void CaseDiv();
    void CasePow();
    void CaseX();
    void CaseSin();
    void CaseSqrt();
    void CaseCos();
    void CaseTan();
    void CaseMod();
    void CaseAsin();
    void CaseAcos();
    void CaseAtan();
    void CaseLn();
    void CaseLog();
    void CaseNumber();
    void FinalCheck();

private:
    bool return_value_;
    int input_cnt_, lex_amount_, bracket_count_;
    const char* input_expression_;

    void ClearMassiveForLexemes();
};

class Model::ReversePolishNotationCalculation {
public:
    ReversePolishNotationCalculation() {
        stack_.reserve(ALLOCATED_SIZE);
        output_.reserve(ALLOCATED_SIZE);
        stack_cnt_ = output_amount_ = -1;
    }
    ~ReversePolishNotationCalculation() {}
    void TranslateToRpn(std::vector<Lexeme>& lexemes_);
    int IsOperator(Lexeme lexeme);

    int ReversePolishNotationCalculator(long double& result);

private:
    std::vector<Lexeme> stack_;
    std::vector<Lexeme> output_;
    int stack_cnt_, output_amount_;
};

bool IsNumber(char c);
}  // namespace s21

#endif  // SRC_MODEL_H_
