#include "model.h"

namespace s21 {
bool Model::MainFunction(const char* input_expression, long double x) {
    bool return_value = true;
    return_value = CheckStrlen(input_expression);
    if (return_value) {
        InputStringParsing input_string_parsing_obj(input_expression);
        if (return_value) {
            return_value = input_string_parsing_obj.Parsing();

            if (return_value) {
                for (int i = 0; input_string_parsing_obj.lexemes_[i].type != -1;
                     i++) {
                    if (input_string_parsing_obj.lexemes_[i].type == VARIABLE) {
                        input_string_parsing_obj.lexemes_[i].type = NUMBER;
                        input_string_parsing_obj.lexemes_[i].value = x;
                    }
                }

                return_value = MainCalculation(input_string_parsing_obj);
            }
        }
    }

    return return_value;
}

bool Model::CheckExpressionCorrectness(const char* input_expression) {
    bool return_value = true;
    return_value = CheckStrlen(input_expression);
    if (return_value) {
        InputStringParsing input_string_parsing_obj(input_expression);
        if (return_value) {
            return_value = input_string_parsing_obj.Parsing();
        }
    }
    return return_value;
}

bool Model::CheckStrlen(const char* input_expression) {
    return (int)strlen(input_expression) > ALLOCATED_SIZE ? false : true;
}

bool Model::MainCalculation(InputStringParsing& input_string_parsing_obj) {
    bool return_value = true;
    ReversePolishNotationCalculation reverse_polish_notation_obj;
    reverse_polish_notation_obj.TranslateToRpn(
        input_string_parsing_obj.lexemes_);
    return_value =
        reverse_polish_notation_obj.ReversePolishNotationCalculator(result_);

    if (return_value && isnan(result_)) {
        return_value = false;
    }

    return return_value;
}

void Model::InputStringParsing::ClearMassiveForLexemes() {
    for (int i = 0; i < ALLOCATED_SIZE; i++) {
        lexemes_[i].type = -1;
    }
}

bool Model::InputStringParsing::Parsing() {
    for (; input_expression_[input_cnt_] != '\0'; input_cnt_++) {
        switch (input_expression_[input_cnt_]) {
            case '(':
                CaseOpenBracket();
                break;
            case ')':
                CaseCloseBracket();
                break;
            case '-':
                CaseMinus();
                break;
            case '+':
                CasePlus();
                break;
            case '*':
                CaseMul();
                break;
            case '/':
                CaseDiv();
                break;
            case '^':
                CasePow();
                break;
            case 'x':
                CaseX();
                break;
            case 's':
                input_cnt_++;
                if (input_expression_[input_cnt_] == 'i') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 'n') {
                        CaseSin();
                    } else {
                        return_value_ = false;
                    }
                } else if (input_expression_[input_cnt_] == 'q') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 'r') {
                        input_cnt_++;
                        if (input_expression_[input_cnt_] == 't') {
                            CaseSqrt();
                        } else {
                            return_value_ = false;
                        }
                    } else {
                        return_value_ = false;
                    }
                } else {
                    return_value_ = false;
                }
                break;
            case 'c':
                input_cnt_++;
                if (input_expression_[input_cnt_] == 'o') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 's') {
                        CaseCos();
                    } else {
                        return_value_ = false;
                    }
                } else {
                    return_value_ = false;
                }
                break;
            case 't':
                input_cnt_++;
                if (input_expression_[input_cnt_] == 'a') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 'n') {
                        CaseTan();
                    } else {
                        return_value_ = false;
                    }
                } else {
                    return_value_ = false;
                }
                break;
            case 'm':
                input_cnt_++;
                if (input_expression_[input_cnt_] == 'o') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 'd') {
                        CaseMod();
                    } else {
                        return_value_ = false;
                    }
                } else {
                    return_value_ = false;
                }
                break;
            case 'a':
                input_cnt_++;
                if (input_expression_[input_cnt_] == 's') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 'i') {
                        input_cnt_++;
                        if (input_expression_[input_cnt_] == 'n') {
                            CaseAsin();
                        } else {
                            return_value_ = false;
                        }
                    } else {
                        return_value_ = false;
                    }
                } else if (input_expression_[input_cnt_] == 'c') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 'o') {
                        input_cnt_++;
                        if (input_expression_[input_cnt_] == 's') {
                            CaseAcos();
                        } else {
                            return_value_ = false;
                        }
                    } else {
                        return_value_ = false;
                    }
                } else if (input_expression_[input_cnt_] == 't') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 'a') {
                        input_cnt_++;
                        if (input_expression_[input_cnt_] == 'n') {
                            CaseAtan();
                        } else {
                            return_value_ = false;
                        }
                    } else {
                        return_value_ = false;
                    }
                } else {
                    return_value_ = false;
                }
                break;
            case 'l':
                input_cnt_++;
                if (input_expression_[input_cnt_] == 'n') {
                    CaseLn();
                } else if (input_expression_[input_cnt_] == 'o') {
                    input_cnt_++;
                    if (input_expression_[input_cnt_] == 'g') {
                        CaseLog();
                    } else {
                        return_value_ = false;
                    }
                } else {
                    return_value_ = false;
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '.':
                CaseNumber();
                break;
            case ' ':
                break;
            default:
                return_value_ = false;
                break;
        }
        if (!return_value_) break;
    }

    FinalCheck();

    return return_value_;
}

void Model::InputStringParsing::CaseOpenBracket() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == UNARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD ||
            lexemes_[lex_amount_ - 1].type == SIN ||
            lexemes_[lex_amount_ - 1].type == COS ||
            lexemes_[lex_amount_ - 1].type == TAN ||
            lexemes_[lex_amount_ - 1].type == ACOS ||
            lexemes_[lex_amount_ - 1].type == ASIN ||
            lexemes_[lex_amount_ - 1].type == ATAN ||
            lexemes_[lex_amount_ - 1].type == SQRT ||
            lexemes_[lex_amount_ - 1].type == LN ||
            lexemes_[lex_amount_ - 1].type == LOG) {
            lexemes_[lex_amount_].type = OPEN_BRACKET;
            lexemes_[lex_amount_].priority = -1;
            bracket_count_++;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = OPEN_BRACKET;
        lexemes_[lex_amount_].priority = 3;
        bracket_count_++;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseCloseBracket() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == NUMBER ||
            lexemes_[lex_amount_ - 1].type == VARIABLE ||
            lexemes_[lex_amount_ - 1].type == CLOSE_BRACKET) {
            lexemes_[lex_amount_].type = CLOSE_BRACKET;
            lexemes_[lex_amount_].priority = -1;
            lex_amount_++;
            bracket_count_--;
        } else {
            return_value_ = false;
        }
    } else {
        return_value_ = false;
    }
}

void Model::InputStringParsing::CaseMinus() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == NUMBER ||
            lexemes_[lex_amount_ - 1].type == VARIABLE ||
            lexemes_[lex_amount_ - 1].type == CLOSE_BRACKET) {
            lexemes_[lex_amount_].type = BINARY_MINUS;
            lexemes_[lex_amount_].priority = 1;
            lex_amount_++;
        } else if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET) {
            lexemes_[lex_amount_].type = UNARY_MINUS;
            lexemes_[lex_amount_].priority = 1;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = UNARY_MINUS;
        lexemes_[lex_amount_].priority = 1;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CasePlus() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == NUMBER ||
            lexemes_[lex_amount_ - 1].type == VARIABLE ||
            lexemes_[lex_amount_ - 1].type == CLOSE_BRACKET) {
            lexemes_[lex_amount_].type = BINARY_PLUS;
            lexemes_[lex_amount_].priority = 1;
            lex_amount_++;
        } else if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET) {
            lexemes_[lex_amount_].type = UNARY_PLUS;
            lexemes_[lex_amount_].priority = 1;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = UNARY_PLUS;
        lexemes_[lex_amount_].priority = 1;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseMul() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == NUMBER ||
            lexemes_[lex_amount_ - 1].type == VARIABLE ||
            lexemes_[lex_amount_ - 1].type == CLOSE_BRACKET) {
            lexemes_[lex_amount_].type = MUL;
            lexemes_[lex_amount_].priority = 2;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        return_value_ = false;
    }
}

void Model::InputStringParsing::CaseDiv() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == NUMBER ||
            lexemes_[lex_amount_ - 1].type == VARIABLE ||
            lexemes_[lex_amount_ - 1].type == CLOSE_BRACKET) {
            lexemes_[lex_amount_].type = DIV;
            lexemes_[lex_amount_].priority = 2;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        return_value_ = false;
    }
}

void Model::InputStringParsing::CasePow() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == NUMBER ||
            lexemes_[lex_amount_ - 1].type == VARIABLE ||
            lexemes_[lex_amount_ - 1].type == CLOSE_BRACKET) {
            lexemes_[lex_amount_].type = POW;
            lexemes_[lex_amount_].priority = 3;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        return_value_ = false;
    }
}

void Model::InputStringParsing::CaseX() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == UNARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = VARIABLE;
            lexemes_[lex_amount_].priority = -1;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = VARIABLE;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseSin() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = SIN;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = SIN;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseSqrt() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = SQRT;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = SQRT;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseCos() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = COS;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = COS;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseTan() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = TAN;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = TAN;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseMod() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == NUMBER ||
            lexemes_[lex_amount_ - 1].type == VARIABLE ||
            lexemes_[lex_amount_ - 1].type == CLOSE_BRACKET) {
            lexemes_[lex_amount_].type = MOD;
            lexemes_[lex_amount_].priority = 2;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        return_value_ = false;
    }
}

void Model::InputStringParsing::CaseAsin() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = ASIN;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = ASIN;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseAcos() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = ACOS;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = ACOS;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseAtan() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = ATAN;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = ATAN;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseLn() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = LN;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = LN;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseLog() {
    if (lex_amount_ != 0) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD) {
            lexemes_[lex_amount_].type = LOG;
            lexemes_[lex_amount_].priority = 4;
            lex_amount_++;
        } else {
            return_value_ = false;
        }
    } else {
        lexemes_[lex_amount_].type = LOG;
        lexemes_[lex_amount_].priority = 4;
        lex_amount_++;
    }
}

void Model::InputStringParsing::CaseNumber() {
    if (((lex_amount_ != 0) &&
         (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
          lexemes_[lex_amount_ - 1].type == UNARY_PLUS ||
          lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
          lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
          lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
          lexemes_[lex_amount_ - 1].type == MUL ||
          lexemes_[lex_amount_ - 1].type == DIV ||
          lexemes_[lex_amount_ - 1].type == POW ||
          lexemes_[lex_amount_ - 1].type == MOD)) ||
        input_cnt_ == 0) {
        char* massive_for_number = new char[ALLOCATED_SIZE]{'\0'};
        massive_for_number[0] = input_expression_[input_cnt_];
        int dot_counter = input_expression_[input_cnt_] == '.' ? 1 : 0;
        input_cnt_++;
        int k = 1;
        for (; IsNumber(input_expression_[input_cnt_]) ||
               input_expression_[input_cnt_] == '.';
             k++) {
            if (input_expression_[input_cnt_] == '.') {
                dot_counter++;
            }
            if (dot_counter > 1) {
                return_value_ = false;
            }
            massive_for_number[k] = input_expression_[input_cnt_];
            input_cnt_++;
        }
        if (input_expression_[input_cnt_] == 'e' && return_value_) {
            massive_for_number[k] = input_expression_[input_cnt_];
            k++;
            input_cnt_++;
            if (input_expression_[input_cnt_] == '-' ||
                input_expression_[input_cnt_] == '+') {
                massive_for_number[k] = input_expression_[input_cnt_];
                k++;
                input_cnt_++;
                if (IsNumber(input_expression_[input_cnt_])) {
                    for (; IsNumber(input_expression_[input_cnt_]); k++) {
                        massive_for_number[k] = input_expression_[input_cnt_];
                        input_cnt_++;
                    }
                } else {
                    return_value_ = false;
                }
            } else if (IsNumber(input_expression_[input_cnt_])) {
                for (; IsNumber(input_expression_[input_cnt_]); k++) {
                    massive_for_number[k] = input_expression_[input_cnt_];
                    input_cnt_++;
                }
            } else {
                return_value_ = false;
            }
        }
        if (return_value_) {
            input_cnt_ -= 1;
            lexemes_[lex_amount_].type = NUMBER;
            lexemes_[lex_amount_].priority = -1;
            lexemes_[lex_amount_].value = atof(massive_for_number);
            lex_amount_++;
        }
        delete massive_for_number;
    } else {
        return_value_ = false;
    }
}

void Model::InputStringParsing::FinalCheck() {
    if (return_value_) {
        if (lexemes_[lex_amount_ - 1].type == OPEN_BRACKET ||
            lexemes_[lex_amount_ - 1].type == UNARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == UNARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_MINUS ||
            lexemes_[lex_amount_ - 1].type == BINARY_PLUS ||
            lexemes_[lex_amount_ - 1].type == MUL ||
            lexemes_[lex_amount_ - 1].type == DIV ||
            lexemes_[lex_amount_ - 1].type == POW ||
            lexemes_[lex_amount_ - 1].type == MOD ||
            lexemes_[lex_amount_ - 1].type == COS ||
            lexemes_[lex_amount_ - 1].type == SIN ||
            lexemes_[lex_amount_ - 1].type == TAN ||
            lexemes_[lex_amount_ - 1].type == ACOS ||
            lexemes_[lex_amount_ - 1].type == ASIN ||
            lexemes_[lex_amount_ - 1].type == ATAN ||
            lexemes_[lex_amount_ - 1].type == SQRT ||
            lexemes_[lex_amount_ - 1].type == LN ||
            lexemes_[lex_amount_ - 1].type == LOG)
            return_value_ = false;

        if (bracket_count_ != 0) return_value_ = false;

        if (input_cnt_ == 0 && lex_amount_ == 0) return_value_ = false;
    }
}

bool IsNumber(char c) {
    if (strchr("0123456789", c) && c != '\0') return true;
    return 0;
}

void Model::ReversePolishNotationCalculation::TranslateToRpn(
    std::vector<Lexeme>& lexemes_) {
    for (int lex_cnt = 0; lexemes_[lex_cnt].type != -1; lex_cnt++) {
        if (lexemes_[lex_cnt].type == NUMBER) {
            output_amount_++;
            output_[output_amount_] = lexemes_[lex_cnt];
        } else if (lexemes_[lex_cnt].type == OPEN_BRACKET) {
            stack_cnt_++;
            stack_[stack_cnt_] = lexemes_[lex_cnt];
        } else if (IsOperator(lexemes_[lex_cnt])) {
            if (stack_cnt_ >= 0) {
                while (IsOperator(stack_[stack_cnt_]) &&
                       stack_[stack_cnt_].priority >=
                           lexemes_[lex_cnt].priority) {
                    output_amount_++;
                    output_[output_amount_] = stack_[stack_cnt_];
                    stack_cnt_--;
                }
            }
            stack_cnt_++;
            stack_[stack_cnt_] = lexemes_[lex_cnt];
        } else if (lexemes_[lex_cnt].type == CLOSE_BRACKET) {
            while (stack_[stack_cnt_].type != OPEN_BRACKET) {
                output_amount_++;
                output_[output_amount_] = stack_[stack_cnt_];
                stack_cnt_--;
            }
            stack_cnt_--;
        }
    }

    while (stack_cnt_ >= 0) {
        output_amount_++;
        output_[output_amount_] = stack_[stack_cnt_];
        stack_cnt_--;
    }
}

int Model::ReversePolishNotationCalculation::IsOperator(Lexeme lexeme) {
    int return_value = 0;
    if (UNARY_MINUS <= lexeme.type && lexeme.type <= LOG) return_value = 1;
    return return_value;
}

int Model::ReversePolishNotationCalculation::ReversePolishNotationCalculator(
    long double& result) {
    int return_value = true;
    std::vector<Lexeme> stack;
    stack.reserve(ALLOCATED_SIZE);
    int stack_cnt_ = -1;
    for (int output_cnt = 0; output_cnt <= output_amount_; output_cnt++) {
        if (output_[output_cnt].type == NUMBER) {
            stack_cnt_++;
            stack[stack_cnt_].value = output_[output_cnt].value;
        } else if (output_[output_cnt].type == UNARY_MINUS) {
            stack[stack_cnt_].value *= -1;
        } else if (output_[output_cnt].type == BINARY_MINUS) {
            stack[stack_cnt_ - 1].value =
                stack[stack_cnt_ - 1].value - stack[stack_cnt_].value;
            stack_cnt_--;
        } else if (output_[output_cnt].type == UNARY_PLUS) {
            stack[stack_cnt_].value *= 1;
        } else if (output_[output_cnt].type == BINARY_PLUS) {
            stack[stack_cnt_ - 1].value =
                stack[stack_cnt_ - 1].value + stack[stack_cnt_].value;
            stack_cnt_--;
        } else if (output_[output_cnt].type == MUL) {
            stack[stack_cnt_ - 1].value =
                stack[stack_cnt_ - 1].value * stack[stack_cnt_].value;
            stack_cnt_--;
        } else if (output_[output_cnt].type == DIV) {
            if (stack[stack_cnt_].value == 0) {
                return_value = false;
            } else {
                stack[stack_cnt_ - 1].value =
                    stack[stack_cnt_ - 1].value / stack[stack_cnt_].value;
                stack_cnt_--;
            }
        } else if (output_[output_cnt].type == POW) {
            stack[stack_cnt_ - 1].value =
                powl(stack[stack_cnt_ - 1].value, stack[stack_cnt_].value);
            stack_cnt_--;
        } else if (output_[output_cnt].type == MOD) {
            if (stack[stack_cnt_].value == 0) {
                return_value = false;
            } else {
                stack[stack_cnt_ - 1].value =
                    fmodl(stack[stack_cnt_ - 1].value, stack[stack_cnt_].value);
                stack_cnt_--;
            }
        } else if (output_[output_cnt].type == COS) {
            stack[stack_cnt_].value = cosl(stack[stack_cnt_].value);
        } else if (output_[output_cnt].type == SIN) {
            stack[stack_cnt_].value = sinl(stack[stack_cnt_].value);
        } else if (output_[output_cnt].type == TAN) {
            stack[stack_cnt_].value = tanl(stack[stack_cnt_].value);
        } else if (output_[output_cnt].type == ACOS) {
            if (fabsl(stack[stack_cnt_].value) > 1) {
                return_value = false;
            } else {
                stack[stack_cnt_].value = acosl(stack[stack_cnt_].value);
            }
        } else if (output_[output_cnt].type == ASIN) {
            if (fabsl(stack[stack_cnt_].value) > 1) {
                return_value = false;
            } else {
                stack[stack_cnt_].value = asinl(stack[stack_cnt_].value);
            }
        } else if (output_[output_cnt].type == ATAN) {
            stack[stack_cnt_].value = atanl(stack[stack_cnt_].value);
        } else if (output_[output_cnt].type == SQRT) {
            if (stack[stack_cnt_].value < 0) {
                return_value = false;
            } else {
                stack[stack_cnt_].value = sqrtl(stack[stack_cnt_].value);
            }
        } else if (output_[output_cnt].type == LN) {
            if (stack[stack_cnt_].value < 0) {
                return_value = false;
            } else {
                stack[stack_cnt_].value = logl(stack[stack_cnt_].value);
            }
        } else if (output_[output_cnt].type == LOG) {
            if (stack[stack_cnt_].value < 0) {
                return_value = false;
            } else {
                stack[stack_cnt_].value = log10l(stack[stack_cnt_].value);
            }
        }
        if (return_value == false) break;
    }
    if (return_value) result = stack[stack_cnt_].value;
    return return_value;
}

bool Model::CheckDoubleCorrectness(const char* input_expr_x, long double& x) {
    bool return_value = true;
    int str_cnt = 0, dot_flag = 0;
    int str_len = (int)strlen(input_expr_x);
    if (str_len == 0) return_value = false;
    if (str_len == 1 && input_expr_x[0] == '.') return_value = false;
    if (input_expr_x[str_cnt] == '+' || input_expr_x[str_cnt] == '-') {
        str_cnt++;
    }
    if (IsNumber(input_expr_x[str_cnt]) || input_expr_x[str_cnt] == '.') {
        while (IsNumber(input_expr_x[str_cnt]) ||
               input_expr_x[str_cnt] == '.') {
            if (input_expr_x[str_cnt] == '.') dot_flag++;
            if (dot_flag > 1) {
                return_value = false;
                break;
            }
            str_cnt++;
        }
        if (input_expr_x[str_cnt] == '\0') {
        } else if (input_expr_x[str_cnt] == 'e') {
            str_cnt++;
            if (input_expr_x[str_cnt] == '\0') {
                return_value = false;
            } else if (input_expr_x[str_cnt] == '+' ||
                       input_expr_x[str_cnt] == '-') {
                str_cnt++;
            }
            if (return_value) {
                if (IsNumber(input_expr_x[str_cnt])) {
                    while (IsNumber(input_expr_x[str_cnt])) {
                        str_cnt++;
                    }
                    if (IsNumber(input_expr_x[str_cnt]) != '\0') {
                        return_value = false;
                    }
                } else {
                    return_value = false;
                }
            }
        } else {
            return_value = false;
        }
    } else {
        return_value = false;
    }

    if (return_value) {
        x = atof(input_expr_x);
    }

    return return_value;
}
}  // namespace s21
