package com.beautysalon.scheduling.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beautysalon.scheduling.model.Scheduling;
import com.beautysalon.scheduling.model.exception.ResourceNotFoundException;
import com.beautysalon.scheduling.repository.SchedulingRepository;
import com.beautysalon.scheduling.shared.ConstumerDTO;
import com.beautysalon.scheduling.shared.ProfessionalUserDTO;
import com.beautysalon.scheduling.shared.SchedulingDTO;
import com.beautysalon.scheduling.shared.TypeTaskDTO;
import com.beautysalon.scheduling.util.interfaces.instancesGlobal;

@Service
public class SchedulingService implements instancesGlobal {
    @Autowired
    private SchedulingRepository schedulingRepository;

    public List<SchedulingDTO> getAll(){
        List<Scheduling> scheduling = schedulingRepository.findAll();
        if(scheduling.isEmpty()){
            throw new ResourceNotFoundException("Nenhum Agendamento Encontrado!");
        }

        List<SchedulingDTO> schedulingDTOs = scheduling.stream()
        .map(sh -> mapper.map(sh,SchedulingDTO.class))
        .collect(Collectors.toList());

        return schedulingDTOs;

    }
    public Optional<SchedulingDTO> getById(Long id){
        schedulingExist(schedulingRepository,id);
        Optional<Scheduling> shOptional = schedulingRepository.findById(id);
        SchedulingDTO schedulingDTO = mapper.map(shOptional.get(), SchedulingDTO.class);
        return Optional.of(schedulingDTO);
    }

    public SchedulingDTO register(SchedulingDTO schedulingDTO){
        Scheduling scheduling = mapper.map(schedulingDTO,Scheduling.class);
        scheduling = schedulingRepository.save(scheduling);
        schedulingDTO.setId(scheduling.getId());
        return schedulingDTO;
    }
    public List<ConstumerDTO> findAllByIdClient(List<Long> ids){
        List<ConstumerDTO> dClientDTOs = (schedulingRepository.findAllByIdClient(ids)).stream()
        .map(cl -> mapper.map(cl,ConstumerDTO.class))
        .collect(Collectors.toList());
        return dClientDTOs;
    }
    public List<TypeTaskDTO> findAllByIdTask(List<Long> ids){
        List<TypeTaskDTO> dTaskDTOs= (schedulingRepository.findAllByIdTypeTask(ids)).stream()
        .map(cl -> mapper.map(cl,TypeTaskDTO.class))
        .collect(Collectors.toList());
        return dTaskDTOs;
    }
    public List<ProfessionalUserDTO> findAllByIdProfessUser(List<Long> ids){
        List<ProfessionalUserDTO> dUserDTOs = (schedulingRepository.findAllByIdUserProfissional(ids)).stream()
        .map(cl -> mapper.map(cl,ProfessionalUserDTO.class))
        .collect(Collectors.toList());
        return dUserDTOs;
    }

    public void delete(Long id){
        schedulingExist(schedulingRepository,id);
        schedulingRepository.deleteById(id);
    }

    public SchedulingDTO update(Long id, SchedulingDTO schedulingDTO){
        schedulingExist(schedulingRepository,id);
        schedulingDTO.setId(id);
        Scheduling scheduling = mapper.map(schedulingDTO,Scheduling.class);
        scheduling = schedulingRepository.save(scheduling);
        return schedulingDTO;
    }
    public void schedulingExist(SchedulingRepository schedulingRepository,Long id){
        if(!schedulingRepository.existsById(id)){
            throw new ResourceNotFoundException("Esse Agendamento não existe!");
        }
    }

}