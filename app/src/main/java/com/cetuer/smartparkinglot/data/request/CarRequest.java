package com.cetuer.smartparkinglot.data.request;

import com.cetuer.smartparkinglot.data.bean.Car;
import com.cetuer.smartparkinglot.data.repository.DataRepository;
import com.kunminx.architecture.ui.callback.UnPeekLiveData;

/**
 * Created by Cetuer on 2022/3/26 23:53.
 * 车辆信息请求
 */
public class CarRequest implements BaseRequest {
    private final UnPeekLiveData<Void> addOrEdit = new UnPeekLiveData<>();
    private final UnPeekLiveData<Car> carInfo = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> canParking = new UnPeekLiveData<>();
    private final UnPeekLiveData<Boolean> canFindCar = new UnPeekLiveData<>();

    /**
     * 获取车辆信息
     */
    public void requestInfo() {
        DataRepository.getInstance().getCarInfo(carInfo::postValue);
    }

    /**
     * 新增或修改车辆信息
     */
    public void requestAddOrEdit(Car car) {
        DataRepository.getInstance().addOrEditCar(addOrEdit::postValue, car);
    }

    /**
     * 是否可以停车
     */
    public void canParking() {
        DataRepository.getInstance().canParking(canParking::postValue);
    }

    /**
     * 是否可以寻车
     */
    public void canFindCar() {
        DataRepository.getInstance().canFindCar(canFindCar::postValue);
    }


    public UnPeekLiveData<Void> getAddOrEdit() {
        return addOrEdit;
    }

    public UnPeekLiveData<Car> getCarInfo() {
        return carInfo;
    }

    public UnPeekLiveData<Boolean> getCanParking() {
        return canParking;
    }

    public UnPeekLiveData<Boolean> getCanFindCar() {
        return canFindCar;
    }
}
