#ifndef SRC_CONTROLLER_H_
#define SRC_CONTROLLER_H_

#include "../Model/model.h"

namespace s21 {
class Controller {
private:
    Model* model_;
    long double result_;
    static Controller* instance;

public:
    Controller();
    ~Controller();
    bool Calculate(const char* input_expression, long double x);
    bool CheckExpressionCorrectness(const char* input_expression);
    long double GetResult();


    static Controller* GetInstance() {
        if (!instance) {
            instance = new Controller;
        }
        return instance;
    }
};
}  // namespace s21

#endif  // SRC_CONTROLLER_H_
